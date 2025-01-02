package TeamGoat.TripSupporter.Domain.Dto.User;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserAndProfileDto {

    private UserDto userDto;

    private UserProfileDto userProfileDto;
}
