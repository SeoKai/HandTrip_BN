package TeamGoat.TripSupporter.Controller.User;

import TeamGoat.TripSupporter.Domain.Dto.User.UserProfileDto;
import TeamGoat.TripSupporter.Service.User.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userProfile")
public class UserProfileController {

    private final UserProfileService userProfileService;

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
}
