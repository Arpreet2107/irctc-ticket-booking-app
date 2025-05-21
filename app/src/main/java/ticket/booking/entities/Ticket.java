package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate; // Use LocalDate for date
import java.util.Objects;

@Getter // Lombok: Generates getters for all fields
@Setter // Lombok: Generates setters for all fields
@NoArgsConstructor // Lombok: Generates a no-argument constructor
@AllArgsConstructor // Lombok: Generates a constructor with all arguments
@Builder // Lombok:  Implements the Builder pattern.
@JsonIgnoreProperties(ignoreUnknown = true) // Jackson: Ignores unknown properties in JSON
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class) // Jackson:  Handles snake_case JSON
public class Ticket {

    private String ticketId;
    private String userId;
    private String source;
    private String destination;
    private LocalDate dateOfTravel; // Changed to LocalDate
    private Train train;

    //  No need for manual constructors, getters, and setters with Lombok.

    /**
     * Provides a formatted string representation of the Ticket.
     * Overriding toString() is generally preferred over creating a custom getTicketInfo()
     * @return A formatted string.
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", userId='" + userId + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", dateOfTravel=" + dateOfTravel + // LocalDate handles formatting well.
                ", train=" + train + //  Assuming Train also has a good toString()
                '}';
    }

    /**
     * Overrides equals() to provide a meaningful comparison of Ticket objects.
     * This is crucial for comparing objects in collections or other logic.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ticketId, ticket.ticketId); //  Compare by a unique identifier (ticketId)
    }

    /**
     * Overrides hashCode() to be consistent with equals().
     * If you override equals(), you MUST override hashCode().
     */
    @Override
    public int hashCode() {
        return Objects.hash(ticketId); // Hash based on the unique identifier.
    }
}
