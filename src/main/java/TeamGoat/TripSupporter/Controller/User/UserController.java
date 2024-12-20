package TeamGoat.TripSupporter.Controller.User;

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
    public ResponseEntity<TokenInfo> register(@Valid @RequestBody UserAndProfileDto userAndProfileDto) {
        TokenInfo tokenInfo = userService.register(userAndProfileDto);
        return ResponseEntity.ok(tokenInfo);
    }

    /**
     * 로그인 처리
     * @param userDto 로그인 요청 데이터 (이메일, 비밀번호)
     * @return JWT 토큰 정보
     */
    @PostMapping(value = "/login",produces = "text/plain;charset=UTF-8")
    public ResponseEntity<TokenInfo> login(@RequestBody UserDto userDto) {
        TokenInfo tokenInfo = userService.login(userDto.getUserEmail(), userDto.getUserPassword());
        return ResponseEntity.ok(tokenInfo);
    }

    /**
     * 로그아웃 처리
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 성공 메시지
     */
    @PostMapping(value = "/logout",produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Authorization 헤더가 잘못되었습니다.");
        }

        String accessToken = authorizationHeader.substring(7); // "Bearer " 제거
        try {
            userService.logout(accessToken);
            response.addHeader("Set-Cookie", "Authorization=; Path=/; Max-Age=0; HttpOnly"); // 쿠키 무효화
            return ResponseEntity.ok("로그아웃 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 비밀번호 찾기 처리
     * @param userDto 비밀번호 찾기 요청 데이터 (이메일, 전화번호)
     * @return 성공 메시지
     */
    @PostMapping(value ="/find-password",produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> findPassword(@RequestBody UserDto userDto) {
        try {
            userService.findPassword(userDto.getUserEmail(), userDto.getUserPhone());
            return ResponseEntity.ok("임시 비밀번호가 이메일로 발송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
