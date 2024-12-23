package TeamGoat.TripSupporter.Domain.User;

import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
import TeamGoat.TripSupporter.Domain.Dto.User.UserProfileDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAndProfileDto {

    private UserDto userDto;

    private UserProfileDto userProfileDto;
}
