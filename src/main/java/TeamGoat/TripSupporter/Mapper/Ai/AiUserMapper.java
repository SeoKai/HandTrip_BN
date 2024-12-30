package TeamGoat.TripSupporter.Mapper.Ai;


import TeamGoat.TripSupporter.Domain.Dto.Ai.AiUserDto;
import TeamGoat.TripSupporter.Domain.Entity.Ai.AiUser;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.User.User;

public class AiUserMapper {

    public static AiUserDto toDto(AiUser aiUser) {
        return AiUserDto.builder()
                .userId(aiUser.getUser().getUserId())
                .locationId(aiUser.getLocation().getLocationId())
                .rating(aiUser.getRating())
                .build();
    }

    public static AiUser toEntity(AiUserDto aiUserDto) {
        return AiUser.builder()
                .user(User.builder().userId(aiUserDto.getUserId()).build())
                .location(Location.builder().locationId(aiUserDto.getLocationId()).build())
                .rating(aiUserDto.getRating())
                .build();
    }
}
