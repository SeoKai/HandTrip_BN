package TeamGoat.TripSupporter.Controller.User;

import TeamGoat.TripSupporter.Config.auth.JwtTokenProvider;
import TeamGoat.TripSupporter.Domain.Dto.Auth.AuthDto;
import TeamGoat.TripSupporter.Domain.Dto.Auth.TokenInfo;
import TeamGoat.TripSupporter.Domain.Dto.User.ChangePasswordRequestDto;
import TeamGoat.TripSupporter.Domain.Dto.User.UserAndProfileDto;
import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
import TeamGoat.TripSupporter.Repository.Ai.AiUserRepository;
import TeamGoat.TripSupporter.Service.Ai.AiRecommendationService;
import TeamGoat.TripSupporter.Service.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AiRecommendationService aiRecommendationService;

    /**
     * 회원가입 처리
     *
     * @param userAndProfileDto 회원가입 요청 데이터
     * @return JWT 토큰 정보
     */
    @PostMapping("/register")
    public ResponseEntity<AuthDto.LoginResponse> register(@Valid @RequestBody UserAndProfileDto userAndProfileDto) {
        log.info("Post /user/register 파라미터 정보 확인 : {}", userAndProfileDto);
        // UserService를 통해 회원가입 후 로그인 응답 반환
        AuthDto.LoginResponse response = userService.register(userAndProfileDto);
        log.info("Post /user/register 반환 정보 확인 : {}", response);
        return ResponseEntity.ok(response);
    }

    /**
     * 비밀번호 찾기 처리
     *
     * @param userDto 비밀번호 찾기 요청 데이터 (이메일, 전화번호)
     * @return 성공 메시지
     */
    @PostMapping("/find-password")
    public ResponseEntity<String> findPassword(@RequestBody UserDto userDto) {
        userService.findPassword(userDto.getUserEmail(), userDto.getUserPhone());
        return ResponseEntity.ok("임시 비밀번호가 이메일로 발송되었습니다.");
    }

    @PostMapping("/find-Email")
    public ResponseEntity<?> findEmail(@RequestBody Map<String, String> payload) {
        String isVerified = payload.get("isVerified");
        String phoneNumber = payload.get("phoneNumber");

        if(isVerified.equals("true")){
            List<String> userEmail = userService.findId(phoneNumber);
            return ResponseEntity.ok(userEmail);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 실패");
        }

    }

    /**
     * 이메일 중복여부 확인 api
     *
     * @param payload post data
     * @return
     */
    @PostMapping("/duplicate-email")
    public ResponseEntity<Boolean> checkDuplicateEmail(@RequestBody Map<String, String> payload) {
        String userEmail = payload.get("userEmail");
        log.info("Get /user/duplicate-email?userEmail={}", userEmail);
        boolean isDuplicate = !userService.isEmailDuplicate(userEmail);
        return ResponseEntity.ok(isDuplicate);
    }


    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestHeader("Authorization") String authorization,
            @RequestBody ChangePasswordRequestDto request) {

        // JWT에서 이메일 추출
        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);

        log.info("Extracted userEmail from JWT: {}", userEmail);

        // 이메일로 userId 조회
        Long userId = aiRecommendationService.getUserIdByEmail(userEmail);
        if (userId == null) {
            throw new IllegalArgumentException("유효하지 않은 이메일입니다.");
        }

        log.info("UserId for email {}: {}", userEmail, userId);

        // 비밀번호 변경 로직 실행
        userService.changePassword(userId, request.getCurrentPassword(), request.getNewPassword());
        log.info("사용자 [{}]의 비밀번호가 성공적으로 변경되었습니다.", userId);

        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }



}
