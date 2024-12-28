package TeamGoat.TripSupporter.Controller.Auth;

import TeamGoat.TripSupporter.Config.auth.JwtTokenProvider;
import TeamGoat.TripSupporter.Domain.Dto.Auth.AuthDto;
import TeamGoat.TripSupporter.Domain.Dto.Auth.TokenInfo;
import TeamGoat.TripSupporter.Service.Auth.AuthService;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthDto.LoginResponse> login(@RequestBody AuthDto.LoginRequest loginRequest) {

        log.info("로그인 요청 데이터: 이메일={}, 비밀번호={}", loginRequest.getUserEmail(), loginRequest.getUserPassword());
        // AuthService에서 TokenInfo 반환
        TokenInfo tokenInfo = authService.authenticateAndGenerateTokens(
                loginRequest.getUserEmail(),
                loginRequest.getUserPassword()
        );
        log.info("토큰 확인 : {}",tokenInfo);

        // AccessToken 만료 시간 계산
        long accessTokenExpiry = System.currentTimeMillis() + jwtTokenProvider.getAccessExpiration();

        // TokenInfo를 LoginResponse로 변환
        AuthDto.LoginResponse loginResponse = new AuthDto.LoginResponse(
                tokenInfo.getAccessToken(),
                tokenInfo.getRefreshToken(),
                accessTokenExpiry
        );
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody AuthDto.LogoutRequest logoutRequest) {
        authService.logout(logoutRequest.getRefreshToken());
        return ResponseEntity.ok("로그아웃 성공");
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthDto.LoginResponse> refreshToken(@RequestBody AuthDto.RefreshRequest refreshRequest) {
        TokenInfo tokenInfo = authService.refreshToken(refreshRequest);

        // 응답에 만료 시간 추가
        AuthDto.LoginResponse loginResponse = new AuthDto.LoginResponse(
                tokenInfo.getAccessToken(),
                tokenInfo.getRefreshToken(),
                System.currentTimeMillis() + jwtTokenProvider.getAccessExpiration()
        );
        return ResponseEntity.ok(loginResponse);
    }
}


