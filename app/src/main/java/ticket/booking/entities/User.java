package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter // Lombok: Generates getters for all fields
@Setter // Lombok: Generates setters for all fields
@NoArgsConstructor // Lombok: Generates a no-argument constructor
@AllArgsConstructor // Lombok: Generates a constructor with all arguments
@Builder // Lombok: Implements the Builder pattern
@JsonIgnoreProperties(ignoreUnknown = true) // Jackson: Ignores unknown properties in JSON
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class) // Jackson: Handles snake_case JSON
public class User {

    private String name;

    @JsonIgnore //  Password should not be included in JSON responses.
    private String password;

    @JsonIgnore //  Hashed password should NEVER be sent in responses.
    private String hashedPassword;

    private List<Ticket> ticketsBooked;

    private String userId;

    //  No need for manual constructors, getters, and setters with Lombok.

    /**
     * Prints the details of the tickets booked by the user.
     * Iterates through the list and prints each ticket.
     */
    public void printTicketsBooked() {
        if (ticketsBooked == null || ticketsBooked.isEmpty()) {
            System.out.println("No tickets booked by this user.");
            return;
        }
        System.out.println("Tickets booked by user " + name + ":");
        for (Ticket ticket : ticketsBooked) {
            System.out.println(ticket); //  Assumes Ticket has a proper toString()
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", ticketsBooked=" + ticketsBooked +
                '}';
    }
}
