package TeamGoat.TripSupporter.Service.User;

import TeamGoat.TripSupporter.Domain.Dto.User.UserProfileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserProfileService {
    // 프로필 조회
    UserProfileDto getProfileByUserEmail(String email);

    // 프로필 수정
    UserProfileDto updateUserProfile(String email, UserProfileDto updatedProfileDto);

    String saveProfileImage (String Email, MultipartFile file) throws IOException;
}