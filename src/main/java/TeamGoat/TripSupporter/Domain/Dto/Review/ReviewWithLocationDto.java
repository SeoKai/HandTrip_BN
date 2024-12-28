package TeamGoat.TripSupporter.Domain.Dto.Review;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.User.UserProfileDto;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewWithLocationDto {

    private ReviewDto reviewDto;
    private LocationDto locationDto;

}
