package ticket.booking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TrainService {

    private static final Logger logger = Logger.getLogger(TrainService.class.getName());
    private List<Train> trainList;
    private final ObjectMapper objectMapper;
    private static final String TRAIN_DB_PATH = "localDB/trains.json";

    public TrainService() throws IOException {
        this(new ObjectMapper());
    }

    public TrainService(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        this.trainList = loadTrainData();
    }

    private List<Train> loadTrainData() throws IOException {
        File trainsFile = new File(TRAIN_DB_PATH);
        if (!trainsFile.exists()) {
            try {
                Files.createDirectories(Paths.get(TRAIN_DB_PATH).getParent());
                Files.createFile(Paths.get(TRAIN_DB_PATH));
                return new ArrayList<>();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error creating train data file: " + e.getMessage(), e);
                throw new IOException("Failed to create train data file.", e);
            }
        }
        try {
            return objectMapper.readValue(trainsFile, new TypeReference<List<Train>>() {});
        } catch (MismatchedInputException e) {
            logger.log(Level.WARNING, "Train data file is empty or corrupted. Returning an empty list.");
            return new ArrayList<>();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading train data: " + e.getMessage(), e);
            throw new IOException("Failed to read train data.", e);
        }
    }

    public List<Train> searchTrains(String source, String destination) throws IOException{ //Added IOException to signature
        if (source == null || source.trim().isEmpty() || destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Source and destination cannot be null or empty.");
        }
        String sourceLower = source.toLowerCase();
        String destinationLower = destination.toLowerCase();

        List<Train> matchingTrains = trainList.stream()
                .filter(train -> isValidTrain(train, sourceLower, destinationLower))
                .collect(Collectors.toList());

        if (matchingTrains.isEmpty()) {
            throw new IOException("No trains found for source: " + source + " and destination: " + destination);
        }
        return matchingTrains;
    }

    public void addTrain(Train newTrain) throws IOException { //Added IOException
        if (newTrain == null) {
            throw new IllegalArgumentException("New train cannot be null.");
        }
        if (trainList.stream().anyMatch(train -> train.getTrainId().equalsIgnoreCase(newTrain.getTrainId()))) {
            updateTrain(newTrain);
            return;
        }
        trainList.add(newTrain);
        saveTrainListToFile();
        logger.info("Train added: " + newTrain);
    }

    public void updateTrain(Train updatedTrain) throws IOException{ //Added IOException
        if (updatedTrain == null) {
            throw new IllegalArgumentException("Updated train cannot be null.");
        }

        Optional<Train> existingTrain = trainList.stream()
                .filter(train -> train.getTrainId().equalsIgnoreCase(updatedTrain.getTrainId()))
                .findFirst();

        if (existingTrain.isPresent()) {
            int index = trainList.indexOf(existingTrain.get());
            trainList.set(index, updatedTrain);
            saveTrainListToFile();
            logger.info("Train updated: " + updatedTrain);
        } else {
            addTrain(updatedTrain);
            logger.warning("Train with id " + updatedTrain.getTrainId() + " not found. Added as new.");
        }
    }

    private void saveTrainListToFile() throws IOException { //Added IOException
        try {
            objectMapper.writeValue(new File(TRAIN_DB_PATH), trainList);
            logger.info("Train data saved to file: " + TRAIN_DB_PATH);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving train data: " + e.getMessage(), e);
            throw new IOException("Failed to save train data.", e);
        }
    }

    private boolean isValidTrain(Train train, String source, String destination) {
        List<String> stationOrder = train.getStations();
        if (stationOrder == null) {
            return false;
        }
        int sourceIndex = stationOrder.indexOf(source);
        int destinationIndex = stationOrder.indexOf(destination);

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }
}
