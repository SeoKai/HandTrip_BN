package TeamGoat.TripSupporter.Domain.Dto.Location;


import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationWithDistanceDto {
    private Location location;
    private Double distance;

}
