package TeamGoat.TripSupporter.Service.User;

import TeamGoat.TripSupporter.Domain.Dto.User.UserProfileDto;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Domain.Entity.User.UserProfile;
import TeamGoat.TripSupporter.Exception.userProfile.UserProfileException;
import TeamGoat.TripSupporter.Repository.User.UserProfileRepository;
import TeamGoat.TripSupporter.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Value("${file.userProfile-upload-dir}")
    private String uploadDir;
    @Value("${file.userProfile-url-prefix}")
    private String urlPrefix;

//    private final String uploadDir = "upload/images/profile/";  // 업로드 디렉토리 경로
//    private final String urlPrefix = "http://localhost:5050/api/userProfile/images/";  // 이미지 접근 URL

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
        log.info("유저 업데이트 진행중?");
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

    @Transactional
    public String saveProfileImage(String Email,MultipartFile file) throws IOException{
        log.info("saveProfileImage is invoked");
        // 파일 크기 제한 예시: 최대 5MB
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IOException("파일 크기가 너무 큽니다. 최대 5MB까지 가능합니다.");
        }

        // 파일 확장자 확인 (예시: jpg, jpeg, png만 허용)
        String originalFilename = file.getOriginalFilename();
        log.info("originalFilename : {} ",originalFilename);


        if (originalFilename == null || !originalFilename.matches(".*\\.(jpg|jpeg|png)$")) {
            throw new IOException("지원되지 않는 파일 형식입니다.");
        }

        UserProfile userProfile = userProfileRepository.findByUser_UserEmail(Email).orElseThrow(()->new UserProfileException("사용자를 찾을 수 없습니다"));

        // 이전 사용자가 사용중인 프로필 사진 url 정보를 가져옴
        String oldImageUrl = userProfile.getProfileImageUrl();
        // 사용자가 기존에 사용중인 프로필 사진 url정보가 있다면 기존 사진을 삭제함
        if (oldImageUrl != null) {
            String oldImagePath = oldImageUrl.replace(urlPrefix, ""); // URL에서 파일 경로 추출
            File oldFile = new File(uploadDir, oldImagePath);
            if (oldFile.exists() && oldFile.isFile()) {
                boolean deleted = oldFile.delete();
                log.info("이전 프로필 이미지 삭제 상태: {}", deleted ? "성공" : "실패");
            }
        }

        // 파일 경로
        String fileName = System.currentTimeMillis() + "-" + UUID.randomUUID() + "-" + originalFilename;
        String directoryPath = uploadDir;
        log.info("fileName : {} , directoryPath : {}", fileName, directoryPath);

        // 프로젝트 루트 디렉토리 경로를 기준으로 파일을 저장할 디렉토리 경로 계산
        String absoluteDirPath = new File("").getAbsolutePath() + File.separator + directoryPath;
        log.info("absoluteDirPath : {}", absoluteDirPath);

        // 디렉토리가 없으면 생성
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IOException("디렉토리 생성에 실패했습니다.");
            }
        }
        log.info("디렉토리 경로 생성 확인 : " + directory.exists());

        // 파일 저장
        String imagePath = absoluteDirPath + File.separator + fileName;
        log.info("imagePath : {} ", imagePath);

        File dest = new File(imagePath);

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.error("파일 저장 중 오류 발생: {}", e.getMessage(), e);
            throw new IOException("파일 저장에 실패했습니다.", e);
        }

        // 저장된 이미지 URL를 userProfile에 저장
        String url = urlPrefix + fileName;
        log.info("url : {} ", url);


        userProfile.updateUserProfileImgUrl(url);
        log.info("유저 프로필 이미지"+userProfile.getProfileImageUrl());
        userProfileRepository.save(userProfile);

        return url;
    }
}
