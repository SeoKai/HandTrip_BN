package TeamGoat.TripSupporter.Domain.Dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String userEmail; // 사용자 이메일
        private String userPassword;  // 사용자 비밀번호
        private String provider; // OAuth2 제공자 (예: Google, Kakao, Naver)

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private String accessToken;  // Access 토큰
        private String refreshToken; // Refresh 토큰
        private long accessTokenExpiry;
    }

    @Data
    @AllArgsConstructor
    public static class LogoutRequest {
        private String refreshToken;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefreshRequest {
        private String refreshToken; // Refresh 토큰
    }
}
