package TeamGoat.TripSupporter.Controller.User;

import TeamGoat.TripSupporter.Domain.Dto.User.UserProfileDto;
import TeamGoat.TripSupporter.Service.User.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userProfile")
@Slf4j
public class UserProfileController {

    private final UserProfileService userProfileService;

//    환경변수 셋업이 잘안되..
    @Value("${file.userProfile-upload-dir}")
    private String uploadDir;
    @Value("${file.userProfile-url-prefix}")
    private String urlPrefix;

//    private final String uploadDir = "upload/images/profile/";  // 업로드 디렉토리 경로 (절대 경로 또는 상대 경로로 설정 가능)


    /**
     * 로그인된 사용자의 프로필 조회
     */
    @GetMapping("/get")
    public UserProfileDto getUserProfile() {
        // SecurityContext에서 현재 사용자 이메일 가져오기
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // 사용자 이메일로 프로필 조회
        return userProfileService.getProfileByUserEmail(userEmail);
    }

    /**
     * 로그인된 사용자의 프로필 업데이트
     *  SecurityContextHolder 를 사용하여 로그인된 사용자의 이메일을 추출한 뒤, 프로필 업데이트 작업을 수행하는 방식은 RESTful API에서 자연스러운 패턴.
     *  엔드포인트를 나눌 필요는 없으며, PUT /api/userProfile을 통해 한 번에 처리 가능
     */
    @PutMapping
    public UserProfileDto updateUserProfile(@RequestBody UserProfileDto updatedProfileDto) {
        // SecurityContext에서 현재 사용자 이메일 가져오기
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // 사용자 이메일로 프로필 업데이트
        return userProfileService.updateUserProfile(userEmail, updatedProfileDto);
    }


    @PostMapping("/uploadProfileImage")
    public ResponseEntity<String> uploadProfileImage(
            @RequestParam("profileImage") MultipartFile file,
            @RequestHeader("Authorization") String authorization
    ) throws IOException {
        log.info("GET /uploadProfileImage");

        // 토큰으로부터 유저정보 추출
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info(file.getOriginalFilename());
        // 이미지 파일을 저장하고 경로를 얻음
        String savedImageUrl = userProfileService.saveProfileImage(userEmail,file);
        log.info("savedImgUrl: " + savedImageUrl);

        return ResponseEntity.ok(savedImageUrl);
    }


    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable("filename") String filename) throws IOException {
        // 실제 파일 경로 설정 (서버 내 경로)
        Path filePath = Paths.get(uploadDir).resolve(filename).normalize();

        log.info("filename : {}",filename);
        // 파일이 존재하지 않으면 FileNotFoundException 던짐
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }

        // 파일을 UrlResource로 변환하여 반환
        Resource resource = new UrlResource(filePath.toUri());
        // 동적으로 MIME 타입 설정
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // 기본 MIME 타입 설정
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))  // MIME 타입을 실제 이미지에 맞게 설정
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }
}

