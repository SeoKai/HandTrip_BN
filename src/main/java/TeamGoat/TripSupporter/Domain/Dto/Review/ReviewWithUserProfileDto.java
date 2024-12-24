package TeamGoat.TripSupporter.Domain.Dto.Review;

import TeamGoat.TripSupporter.Domain.Dto.User.UserProfileDto;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewWithUserProfileDto {

    private ReviewDto reivewDto;
    private UserProfileDto userProfileDto;

}
