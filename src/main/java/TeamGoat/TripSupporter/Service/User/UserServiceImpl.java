package TeamGoat.TripSupporter.Service.User;

import TeamGoat.TripSupporter.Config.auth.JwtTokenProvider;
import TeamGoat.TripSupporter.Domain.Dto.Auth.AuthDto;
import TeamGoat.TripSupporter.Domain.Dto.Auth.TokenInfo;
import TeamGoat.TripSupporter.Domain.Dto.User.UserAndProfileDto;
import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
import TeamGoat.TripSupporter.Domain.Entity.Auth.AuthToken;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Domain.Entity.User.UserProfile;
import TeamGoat.TripSupporter.Domain.Enum.UserRole;
import TeamGoat.TripSupporter.Domain.Enum.UserStatus;
import TeamGoat.TripSupporter.Repository.Auth.AuthTokenRepository;
import TeamGoat.TripSupporter.Repository.User.UserProfileRepository;
import TeamGoat.TripSupporter.Repository.User.UserRepository;
import TeamGoat.TripSupporter.Service.Auth.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserProfileRepository userProfileRepository;
    private final AuthTokenRepository authTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    @Transactional
    public AuthDto.LoginResponse register(UserAndProfileDto userAndProfileDto) {
        // 중복 체크
        if (userRepository.existsByUserEmail(userAndProfileDto.getUserDto().getUserEmail()) ||
                userProfileRepository.existsByUserNickname(userAndProfileDto.getUserProfileDto().getUserNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일 또는 닉네임입니다.");
        }

        // 사용자 생성 및 저장
        User user = User.builder()
                .userEmail(userAndProfileDto.getUserDto().getUserEmail())
                .userPassword(passwordEncoder.encode(userAndProfileDto.getUserDto().getUserPassword()))
                .userPhone(userAndProfileDto.getUserDto().getUserPhone())
                .userRole(UserRole.USER)
                .userStatus(UserStatus.valueOf("ACTIVE"))
                .build();
        userRepository.save(user);

        // 사용자 프로필 생성 및 저장
        UserProfile userProfile = UserProfile.builder()
                .user(user)
                .userNickname(userAndProfileDto.getUserProfileDto().getUserNickname())
                .build();
        userProfileRepository.save(userProfile);

        // 회원가입 후 로그인 처리
        AuthDto.LoginRequest loginRequest = new AuthDto.LoginRequest(
                user.getUserEmail(),
                userAndProfileDto.getUserDto().getUserPassword()
        );

        return authService.login(loginRequest); // AuthService를 통해 로그인 처리
    }


    // 나머지 메서드는 기존 로직 유지
    @Override
    @Transactional
    public void updateUser(String email, UserDto updatedData) {
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (updatedData.getUserPassword() != null && !updatedData.getUserPassword().isBlank()) {
            user.updatePassword(passwordEncoder.encode(updatedData.getUserPassword()));
        }

        if (updatedData.getUserPhone() != null && !updatedData.getUserPhone().isBlank()) {
            user.updatePhone(updatedData.getUserPhone());
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        userRepository.delete(user);
    }

    @Override
    public void findPassword(String email, String phone) {
        User user = userRepository.findByUserEmailAndUserPhone(email, phone)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 확인할 수 없습니다."));

        String tempPassword = generateTempPassword();
        user.updatePassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        sendEmail(email, "임시 비밀번호 안내", "임시 비밀번호는 " + tempPassword + "입니다.");
        log.info("임시 비밀번호가 이메일 [{}]로 전송되었습니다.", email);
    }
    @Override
    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByUserEmail(email);
    }

    @Override
    public boolean isNicknameDuplicate(String nickname) {
        return userProfileRepository.existsByUserNickname(nickname);
    }

    @Override
    public String findId(String phone) {
        return userRepository.findByUserPhone(phone)
                .map(User::getUserEmail)
                .orElse(null);
    }

    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private void sendEmail(String to, String subject, String body) {
        log.info("이메일 [{}]로 [{}]를 전송합니다. 내용: {}", to, subject, body);
    }
}
