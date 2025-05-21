package ticket.booking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserBookingService {

    private static final Logger logger = Logger.getLogger(UserBookingService.class.getName());
    private final ObjectMapper objectMapper;
    private List<User> userList;
    private User user;  //  Consider if this should be a constructor parameter or handled differently
    private static final String USER_FILE_PATH = "app/src/main/java/ticket/booking/localDb/users.json";

    public UserBookingService(User user) throws IOException {
        this(user, new ObjectMapper()); // Call the other constructor
    }

    public UserBookingService() throws IOException {
        this(null, objectMapper);
    }

    public UserBookingService(User user, ObjectMapper objectMapper) throws IOException {
        this.user = user;
        this.objectMapper = objectMapper;
        this.userList = loadUserListFromFile();
    }

    private List<User> loadUserListFromFile() throws IOException {
        File usersFile = new File(USER_FILE_PATH);
        if (!usersFile.exists()) {
            try {
                Files.createDirectories(Paths.get(USER_FILE_PATH).getParent());
                Files.createFile(Paths.get(USER_FILE_PATH));
                return new ArrayList<>();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error creating user data file: " + e.getMessage(), e);
                throw new IOException("Failed to create user data file.", e);
            }
        }
        try {
            return objectMapper.readValue(usersFile, new TypeReference<List<User>>() {});
        } catch (MismatchedInputException e) {
            logger.log(Level.WARNING, "User data file is empty or corrupted. Returning an empty list.");
            return new ArrayList<>();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading user data file: " + e.getMessage(), e);
            throw new IOException("Failed to read user data file.", e);
        }
    }

    public boolean loginUser() {
        if (user == null) {
            throw new IllegalArgumentException("User object must be provided for login.");
        }
        Optional<User> foundUser = userList.stream().filter(user1 ->
                user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword())
        ).findFirst();
        return foundUser.isPresent();
    }

    public boolean signUp(User newUser) throws IOException { // Added IOException
        if (newUser == null) {
            throw new IllegalArgumentException("User object must be provided for sign up.");
        }
        // Check if user already exists
        if (userList.stream().anyMatch(u -> u.getUserId().equals(newUser.getUserId()))) {
            logger.warning("User with ID " + newUser.getUserId() + " already exists.");
            return false; // Or throw an exception:  throw new UserAlreadyExistsException("User already exists");
        }
        userList.add(newUser);
        saveUserListToFile();
        logger.info("User signed up: " + newUser.getName());
        return true;
    }

    private void saveUserListToFile() throws IOException {
        try {
            objectMapper.writeValue(new File(USER_FILE_PATH), userList);
            logger.info("User data saved to file: " + USER_FILE_PATH);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving user data to file: " + e.getMessage(), e);
            throw new IOException("Failed to save user data.", e);
        }
    }

    public void fetchBookings() {
        if (user == null) {
            throw new IllegalArgumentException("User object must be provided to fetch bookings.");
        }
        Optional<User> userFetched = userList.stream().filter(user1 ->
                user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword())
        ).findFirst();
        if (userFetched.isPresent()) {
            userFetched.get().printTicketsBooked();
        } else {
            logger.warning("User " + user.getName() + " not found.");
            //  Consider throwing an exception here.  What should happen if the user isn't found?
        }
    }

    public boolean cancelBooking(String ticketId) throws IOException { // Added IOException
        if (user == null) {
            throw new IllegalArgumentException("User object must be provided to cancel a booking.");
        }
        if (ticketId == null || ticketId.isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty.");
        }

        Optional<Ticket> ticketToRemove = user.getTicketsBooked().stream()
                .filter(ticket -> ticket.getTicketId().equals(ticketId))
                .findFirst();

        if (ticketToRemove.isPresent()) {
            user.getTicketsBooked().remove(ticketToRemove.get()); // Remove the ticket
            saveUserListToFile(); // Persist the change
            logger.info("Ticket with ID " + ticketId + " has been canceled.");
            return true;
        } else {
            logger.warning("No ticket found with ID " + ticketId + " for user " + user.getName());
            return false;
        }
    }

    public List<Train> getTrains(String source, String destination) throws IOException {
        TrainService trainService = new TrainService();
        return trainService.searchTrains(source, destination);
    }

    public List<List<Integer>> fetchSeats(Train train) {
        if (train == null) {
            throw new IllegalArgumentException("Train object cannot be null.");
        }
        return train.getSeats();
    }

    public boolean bookTrainSeat(Train train, int row, int seat) throws IOException {
        if (train == null) {
            throw new IllegalArgumentException("Train object cannot be null.");
        }
        if (row < 0 || row >= train.getSeats().size() || seat < 0 || seat >= train.getSeats().get(row).size()) {
            throw new IllegalArgumentException("Invalid row or seat index.");
        }

        List<List<Integer>> seats = train.getSeats();  // Get the seats
        if (seats.get(row).get(seat) == 0) {
            seats.get(row).set(seat, 1); // Book the seat
            train.setSeats(seats);       // Set the updated seats back to the train
            TrainService trainService = new TrainService(); //Use TrainService
            trainService.updateTrain(train); // Update the train in the "database"
            return true;
        } else {
            return false;
        }
    }
}

