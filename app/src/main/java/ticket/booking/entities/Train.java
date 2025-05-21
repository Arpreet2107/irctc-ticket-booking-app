package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter // Lombok: Generates getters for all fields
@Setter // Lombok: Generates setters for all fields
@NoArgsConstructor // Lombok: Generates a no-argument constructor
@AllArgsConstructor // Lombok: Generates a constructor with all arguments
@Builder // Lombok: Implements the Builder pattern
@JsonIgnoreProperties(ignoreUnknown = true) // Jackson: Ignores unknown properties in JSON
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class) // Jackson: Handles snake_case JSON
public class Train {

    private String trainId;
    private String trainNo;
    private List<List<Integer>> seats;
    private Map<String, String> stationTimes;
    private List<String> stations;

    // No need for manual constructors, getters, and setters with Lombok.

    /**
     * Provides a formatted string representation of the Train.
     * Overriding toString() is generally preferred over creating a custom getTrainInfo()
     *
     * @return A formatted string.
     */
    @Override
    public String toString() {
        return "Train{" +
                "trainId='" + trainId + '\'' +
                ", trainNo='" + trainNo + '\'' +
                ", seats=" + seats +
                ", stationTimes=" + stationTimes +
                ", stations=" + stations +
                '}';
    }

    /**
     * Overrides equals() to provide a meaningful comparison of Train objects.
     * It's crucial for comparing objects in collections or other logic.  Compares by trainId
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return Objects.equals(trainId, train.trainId);
    }

    /**
     * Overrides hashCode() to be consistent with equals().
     * If you override equals(), you MUST override hashCode().
     */
    @Override
    public int hashCode() {
        return Objects.hash(trainId);
    }

    public int getTrainName() {
        return 0;
    }
}
