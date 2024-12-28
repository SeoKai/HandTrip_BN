package TeamGoat.TripSupporter.Controller.User;

import TeamGoat.TripSupporter.Domain.Dto.Auth.AuthDto;
import TeamGoat.TripSupporter.Domain.Dto.Auth.TokenInfo;
import TeamGoat.TripSupporter.Domain.Dto.User.UserAndProfileDto;
import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
import TeamGoat.TripSupporter.Service.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 처리
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
     * @param userDto 비밀번호 찾기 요청 데이터 (이메일, 전화번호)
     * @return 성공 메시지
     */
    @PostMapping("/find-password")
    public ResponseEntity<String> findPassword(@RequestBody UserDto userDto) {
        userService.findPassword(userDto.getUserEmail(), userDto.getUserPhone());
        return ResponseEntity.ok("임시 비밀번호가 이메일로 발송되었습니다.");
    }
}
