package TeamGoat.TripSupporter.Mapper.User;

import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
import TeamGoat.TripSupporter.Domain.Dto.User.UserProfileDto;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Domain.Entity.User.UserProfile;
import TeamGoat.TripSupporter.Domain.Enum.UserRole;
import TeamGoat.TripSupporter.Domain.Enum.UserStatus;
import TeamGoat.TripSupporter.Exception.UserNotFoundException;
import TeamGoat.TripSupporter.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository userRepository;

    // UserDto -> User 엔티티 변환 메서드
    public User toUserEntity(UserDto userDto) {
        if(userDto.getUserRole() == null || userDto.getUserRole().equals("")){
            userDto.setUserRole(UserRole.USER); // 기본 Role 설정
        }
        if(userDto.getUserStatus() == null || userDto.getUserStatus().equals("")){
            userDto.setUserStatus(UserStatus.ACTIVE); // 기본 Status 설정
        }
        return User.builder()
                .userId(userDto.getUserId())
                .userEmail(userDto.getUserEmail())
                .password(userDto.getPassword())
                .userRole(userDto.getUserRole())
                .userStatus(userDto.getUserStatus())
                .userCreatedAt(userDto.getUserCreatedAt())
                .build();
    }

    public UserDto toUserDto(User user){
        return UserDto.builder()
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .password(user.getPassword())
                .userRole(user.getUserRole())
                .userStatus(user.getUserStatus())
                .userCreatedAt(user.getUserCreatedAt())
                .build();
    }

    // UserProfileDto -> UserProfile 엔티티 변환 메서드
    public UserProfile toProfileEntity(UserProfileDto userProfileDto) {

        User user = userRepository.findById(userProfileDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다 ID: " + userProfileDto.getUserId()));

        return UserProfile.builder()
                .userProfileId(userProfileDto.getUserProfileId())
                .nickname(userProfileDto.getNickname())
                .phoneNumber(userProfileDto.getPhoneNumber())
                .profileImageUrl(userProfileDto.getProfileImageUrl())
                .userBio(userProfileDto.getUserBio())
                .user(user)  // User 엔티티를 설정
                .build();
    }

    // UserProfile 엔티티 -> UserProfileDto 변환 메서드
    public static UserProfileDto toProfileDto(UserProfile userProfile) {
        return UserProfileDto.builder()
                .userProfileId(userProfile.getUserProfileId())
                .nickname(userProfile.getNickname())
                .phoneNumber(userProfile.getPhoneNumber())
                .profileImageUrl(userProfile.getProfileImageUrl())
                .userBio(userProfile.getUserBio())
                .build();
    }


}
