package TeamGoat.TripSupporter.Domain.Dto.Favorite;

import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
import lombok.*;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFavoriteLocationsDto {

    private List<UserDto> user;
    private List<LocationResponseDto> LocationResponseDto; // 즐겨찾기한 장소들의 목록

}
