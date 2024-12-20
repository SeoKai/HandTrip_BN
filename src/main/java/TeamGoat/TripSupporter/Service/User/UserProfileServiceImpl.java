package TeamGoat.TripSupporter.Service.User;

import TeamGoat.TripSupporter.Domain.Dto.User.UserProfileDto;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Domain.Entity.User.UserProfile;
import TeamGoat.TripSupporter.Exception.userProfile.UserProfileException;
import TeamGoat.TripSupporter.Repository.User.UserProfileRepository;
import TeamGoat.TripSupporter.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfileDto getProfileByUserEmail(String email) {
        // 이메일로 사용자 조회

        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UserProfileException("유효하지 않은 이메일입니다."));

        // 프로필 정보 반환
        return UserProfileDto.builder()
                .userProfileId(user.getUserProfile().getUserProfileId())
                .userNickname(user.getUserProfile().getUserNickname())
                .profileImageUrl(user.getUserProfile().getProfileImageUrl())
                .userBio(user.getUserProfile().getUserBio())
                .build();
    }

    @Transactional
    public UserProfileDto updateUserProfile(String userEmail, UserProfileDto updatedProfileDto) {
        // User를 이메일로 조회
        if (userEmail == null || userEmail.isEmpty()) {
            throw new UserProfileException("유효하지 않은 이메일입니다.");
        }
        if (updatedProfileDto == null) {
            throw new UserProfileException("업데이트할 프로필 정보가 없습니다.");
        }

        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자가 존재하지 않습니다."));

        // UserProfile 업데이트
        UserProfile userProfile = user.getUserProfile();

        UserProfile updatedProfile = UserProfile.builder()
                .userProfileId(userProfile.getUserProfileId())
                .userNickname(updatedProfileDto.getUserNickname() != null
                        ? updatedProfileDto.getUserNickname()
                        : userProfile.getUserNickname())
                .profileImageUrl(updatedProfileDto.getProfileImageUrl() != null
                        ? updatedProfileDto.getProfileImageUrl()
                        : userProfile.getProfileImageUrl())
                .userBio(updatedProfileDto.getUserBio() != null
                        ? updatedProfileDto.getUserBio()
                        : userProfile.getUserBio())
                .user(user) // User 객체 설정
                .build();

        // 새로 생성된 UserProfile 저장
        userProfileRepository.save(updatedProfile);

        // 업데이트된 데이터를 반환
        return UserProfileDto.builder()
                .userProfileId(updatedProfile.getUserProfileId())
                .userNickname(updatedProfile.getUserNickname())
                .profileImageUrl(updatedProfile.getProfileImageUrl())
                .userBio(updatedProfile.getUserBio())
                .build();
    }
}
