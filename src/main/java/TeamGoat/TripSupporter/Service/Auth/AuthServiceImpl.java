package TeamGoat.TripSupporter.Service.Auth;

import TeamGoat.TripSupporter.Config.auth.JwtTokenProvider;
import TeamGoat.TripSupporter.Domain.Dto.Auth.AuthDto;
import TeamGoat.TripSupporter.Domain.Dto.Auth.TokenInfo;
import TeamGoat.TripSupporter.Domain.Entity.Auth.AuthToken;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Repository.Auth.AuthTokenRepository;
import TeamGoat.TripSupporter.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTokenRepository authTokenRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, AuthTokenRepository authTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authTokenRepository = authTokenRepository;
    }

    @Override
    public AuthDto.LoginResponse login(AuthDto.LoginRequest loginRequest) {

        log.info("AuthServiceImpl login invoke 파라미터 확인, loginRequest: {}", loginRequest);
        if (loginRequest.getUserPassword() == null) {
            throw new IllegalArgumentException("비밀번호가 null입니다.");
        }


        // 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserEmail(), loginRequest.getUserPassword())
        );
        log.info("사용자 인증 성공: {}", authentication);

        // provider 처리
        String provider = loginRequest.getProvider() != null ? loginRequest.getProvider() : "default";
        String accessToken = jwtTokenProvider.generateAccessToken(loginRequest.getUserEmail(), provider);
        String refreshToken = jwtTokenProvider.generateRefreshToken(loginRequest.getUserEmail());

        // 기존 토큰 제거 및 새 토큰 저장
        authTokenRepository.findByUserEmail(loginRequest.getUserEmail())
                .ifPresent(authTokenRepository::delete);

        AuthToken authToken = new AuthToken();
        authToken.setUserEmail(loginRequest.getUserEmail());
        authToken.setRefreshToken(refreshToken);
        authToken.setExpiration(System.currentTimeMillis() + jwtTokenProvider.getRefreshExpiration());
        authTokenRepository.save(authToken);

        long accessTokenExpiry = System.currentTimeMillis() + jwtTokenProvider.getAccessExpiration();
        log.info("accessTokenExpiry 만료 시간 계산 확인 accessTokenExpiry : {}",accessTokenExpiry);
        return new AuthDto.LoginResponse(accessToken, refreshToken, accessTokenExpiry);
    }


    @Override
    public TokenInfo authenticateAndGenerateTokens(String email, String password) {
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, user.getUserPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String provider = user.getProvider() != null ? user.getProvider() : "default";
        String accessToken = jwtTokenProvider.generateAccessToken(email, provider);
        String refreshToken = jwtTokenProvider.generateRefreshToken(email);

        return new TokenInfo(accessToken, refreshToken);
    }


    @Override
        @Transactional
        public void logout (String refreshToken){
            AuthToken authToken = authTokenRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Refresh 토큰입니다."));
            authTokenRepository.delete(authToken);
        }

    @Override
    @Transactional
    public TokenInfo refreshToken(AuthDto.RefreshRequest refreshRequest) {
        AuthToken authToken = authTokenRepository.findByRefreshToken(refreshRequest.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Refresh 토큰입니다."));


        if (!jwtTokenProvider.isTokenValid(authToken.getRefreshToken(), authToken.getUserEmail())) {
            authTokenRepository.delete(authToken);
            throw new IllegalArgumentException("Refresh 토큰이 만료되었습니다.");
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(authToken.getUserEmail(), "OAuth2");
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(authToken.getUserEmail());
        authToken.setRefreshToken(newRefreshToken);
        authTokenRepository.save(authToken);

        return new TokenInfo(newAccessToken, newRefreshToken);
    }




    @Transactional
    public void deleteExpiredTokens() {
        long currentTime = System.currentTimeMillis();
        List<AuthToken> expiredTokens = authTokenRepository.findExpiredTokens(currentTime);

        if (!expiredTokens.isEmpty()) {
            authTokenRepository.deleteAll(expiredTokens);
            log.info("만료된 RefreshToken {}개 삭제됨.", expiredTokens.size());
        }
    }

}


