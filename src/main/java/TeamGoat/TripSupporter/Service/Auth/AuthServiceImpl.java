package TeamGoat.TripSupporter.Service.Auth;

import TeamGoat.TripSupporter.Config.auth.JwtTokenProvider;
import TeamGoat.TripSupporter.Domain.Dto.Auth.AuthDto;
import TeamGoat.TripSupporter.Domain.Entity.Auth.AuthToken;
import TeamGoat.TripSupporter.Repository.Auth.AuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTokenRepository authTokenRepository;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, AuthTokenRepository authTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authTokenRepository = authTokenRepository;
    }

    @Override
    public AuthDto.LoginResponse login(AuthDto.LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserEmail(), loginRequest.getPassword())
        );

        String accessToken = jwtTokenProvider.generateAccessToken(loginRequest.getUserEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(loginRequest.getUserEmail());

        AuthToken authToken = new AuthToken();
        authToken.setUserEmail(loginRequest.getUserEmail());
        authToken.setRefreshToken(refreshToken);
        authToken.setExpiration(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
        authTokenRepository.save(authToken);

        return new AuthDto.LoginResponse(accessToken, refreshToken);
    }

    @Override
    public String logout(String token) {
        String userEmail = jwtTokenProvider.extractUserEmail(token);
        authTokenRepository.deleteByUserEmail(userEmail);
        return userEmail;
    }

    @Override
    public AuthDto.LoginResponse refreshToken(AuthDto.RefreshRequest refreshRequest) {
        // Refresh 토큰을 데이터베이스에서 검색
        AuthToken authToken = authTokenRepository.findByRefreshToken(refreshRequest.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Refresh 토큰입니다."));

        // 토큰 검증
        if (!jwtTokenProvider.isTokenValid(authToken.getRefreshToken(), authToken.getUserEmail())) {
            throw new IllegalArgumentException("Refresh 토큰이 유효하지 않습니다.");
        }

        // 새로운 Access 토큰 생성
        String newAccessToken = jwtTokenProvider.generateAccessToken(authToken.getUserEmail());

        return new AuthDto.LoginResponse(newAccessToken, authToken.getRefreshToken());
    }
}
