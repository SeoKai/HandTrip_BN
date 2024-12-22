package TeamGoat.TripSupporter.Service.Auth;

import TeamGoat.TripSupporter.Domain.Dto.Auth.AuthDto;

public interface AuthService {

    // 로그인 처리 메서드
    AuthDto.LoginResponse login(AuthDto.LoginRequest loginRequest);

    // 토큰 갱신 처리 메서드
    AuthDto.LoginResponse refreshToken(AuthDto.RefreshRequest refreshRequest);

    // 로그아웃 처리 메서드
    String logout(String token);
}
