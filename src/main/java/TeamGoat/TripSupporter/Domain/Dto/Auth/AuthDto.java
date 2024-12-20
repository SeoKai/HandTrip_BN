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
        private String password;  // 사용자 비밀번호
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private String accessToken;  // Access 토큰
        private String refreshToken; // Refresh 토큰
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefreshRequest {
        private String refreshToken; // Refresh 토큰
    }
}
