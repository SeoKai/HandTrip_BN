package TeamGoat.TripSupporter.Service.Auth;

import TeamGoat.TripSupporter.Domain.Dto.Auth.AuthDto;
import TeamGoat.TripSupporter.Domain.Dto.Auth.TokenInfo;

public interface AuthService {

    // 로그인 처리 메서드
    AuthDto.LoginResponse login(AuthDto.LoginRequest loginRequest);

    TokenInfo authenticateAndGenerateTokens(String email, String password); // 로그인 처리
    void logout(String refreshToken); // 로그아웃 처리
    TokenInfo refreshToken(AuthDto.RefreshRequest refreshRequest); // 토큰 갱신 처리
}
