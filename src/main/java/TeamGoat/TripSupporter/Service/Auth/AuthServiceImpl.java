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
        log.info("AuthServiceImpl login invoke 파라미터 확인, loginRequest : {}", loginRequest);
        if (loginRequest.getUserPassword() == null) {
            throw new IllegalArgumentException("비밀번호가 null입니다.");
        }

        // 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserEmail(), loginRequest.getUserPassword())
        );
        log.info("authentication 생성 확인 authentication : {}",authentication);

        // AccessToken 및 RefreshToken 생성
        log.info("loginRequest로 부터 getUserEamil 확인 .. loginRequest : {}, userEmail : {}",loginRequest,loginRequest.getUserEmail());

        String accessToken = jwtTokenProvider.generateAccessToken(loginRequest.getUserEmail());
        log.info("accessToken 생성 확인 accessToken : {} ",accessToken);
        String refreshToken = jwtTokenProvider.generateRefreshToken(loginRequest.getUserEmail());
        log.info("refreshToken 생성 확인 refreshToken : {} ",refreshToken);

        // 기존 AuthToken 제거
        authTokenRepository.findByUserEmail(loginRequest.getUserEmail())
                .ifPresent(authTokenRepository::delete);

        // 새 AuthToken 저장
        AuthToken authToken = new AuthToken();
        authToken.setUserEmail(loginRequest.getUserEmail());
        authToken.setRefreshToken(refreshToken);
        authToken.setExpiration(System.currentTimeMillis() + jwtTokenProvider.getRefreshExpiration()); // RefreshToken 만료 시간
        authTokenRepository.save(authToken);

        // AccessToken 만료 시간 계산
        long accessTokenExpiry = System.currentTimeMillis() + jwtTokenProvider.getAccessExpiration();
        log.info("accessTokenExpiry 만료 시간 계산 확인 accessTokenExpiry : {}",accessTokenExpiry);

        // LoginResponse 반환
        AuthDto.LoginResponse response = new AuthDto.LoginResponse(accessToken, refreshToken, accessTokenExpiry);
        log.info("AuthServiceImpl login invoke 반환 확인, response : {}", response);
        return response;
    }


    @Override
    public TokenInfo authenticateAndGenerateTokens(String email, String password) {
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        log.info("로그인 요청 이메일: {}", email);
        log.info("로그인 요청 비밀번호: {}", password);

        if (!passwordEncoder.matches(password, user.getUserPassword())) {
            log.warn("비밀번호 불일치. 입력된 비밀번호: {}", password);
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
            String accessToken = jwtTokenProvider.generateAccessToken(email);
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

        // RefreshToken 유효성 검사
        if (!jwtTokenProvider.isTokenValid(authToken.getRefreshToken(), authToken.getUserEmail())) {
            authTokenRepository.delete(authToken); // 만료된 토큰 삭제
            throw new IllegalArgumentException("Refresh 토큰이 유효하지 않습니다.");
        }

        // AccessToken 갱신 및 새로운 RefreshToken 생성
        String newAccessToken = jwtTokenProvider.generateAccessToken(authToken.getUserEmail());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(authToken.getUserEmail());

        // 기존 RefreshToken 업데이트
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


