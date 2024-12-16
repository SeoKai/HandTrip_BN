package TeamGoat.TripSupporter.Service.Impl;

import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Repository.UserRepository;
import TeamGoat.TripSupporter.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 구현체.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입 메서드.
     * 사용자 정보를 저장하고 비밀번호를 암호화하여 저장.
     *
     * @param userDto 회원 가입에 필요한 사용자 정보
     * @return 회원 가입 성공 여부
     */
    @Override
    public boolean register(UserDto userDto) {
        if (userDto == null){
            throw new IllegalArgumentException("사용자 정보는 빈 값 일 수 없습니다.");
        }
        if (isEmailDuplicate(userDto.getUserEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        } if (isNicknameDuplicate(userDto.getUserNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        User user = User.builder()
                .userEmail(userDto.getUserEmail())
                .userPassword(passwordEncoder.encode(userDto.getUserPassword()))
                .userNickname(userDto.getUserNickname())
                .userPhone(userDto.getUserPhone())
                .build();
        userRepository.save(user);
        return true;
    }

    /**
     * 로그인 메서드.
     * 이메일과 비밀번호를 확인하여 로그인 성공 여부를 반환.
     *
     * @param email    사용자 이메일
     * @param password 사용자 비밀번호
     * @return 로그인 성공 여부
     */
    @Override
    public boolean login(String email, String password) {
        Optional<User> User = userRepository.findByEmail(email);
        if (User.isEmpty()) {
            throw new IllegalArgumentException("이메일이 존재하지 않습니다.");
        }
        User user = User.get();
        if (!passwordEncoder.matches(password, user.getUserPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return true;
    }

    /**
     * 아이디(이메일) 찾기 메서드.
     * 전화번호로 이메일을 조회하여 반환.
     *
     * @param phone 사용자 전화번호
     * @return 등록된 이메일 주소 (없을 경우 null)
     */
    @Override
    public String findId(String phone) {
        Optional<User> optionalUser = userRepository.findByEmail(phone);
        return optionalUser.map(User::getUserEmail).orElse(null);
    }

    /**
     * 이메일 중복 확인 메서드.
     * 이메일이 데이터베이스에 존재하는지 확인.
     *
     * @param email 확인할 이메일
     * @return 이메일이 이미 존재하면 true, 아니면 false
     */
    @Override
    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByUserEmail(email);
    }

    /**
     * 비밀번호 찾기 메서드.
     * 이메일과 전화번호로 사용자를 조회한 후 임시 비밀번호를 생성하여 반환.
     *
     * @param email 사용자 이메일
     * @param phone 사용자 전화번호
     * @return 비밀번호 재설정 링크 또는 상태 메시지
     */
    @Override
    public String findPassword(String email, String phone) {
        Optional<User> optionalUser = userRepository.findByEmailAndPhone(email, phone);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("사용자 정보가 일치하지 않습니다.");
        }
        User user = optionalUser.get();
        String tempPassword = generateTempPassword();
        User.builder().userPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);
        return tempPassword;
    }

    /**
     * 닉네임 중복 확인 메서드.
     * 닉네임이 데이터베이스에 존재하는지 확인.
     *
     * @param nickname 확인할 닉네임
     * @return 닉네임이 이미 존재하면 true, 아니면 false
     */
    @Override
    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    /**
     * 회원 탈퇴 메서드.
     * 사용자 정보를 삭제.
     *
     * @return 탈퇴 성공 여부
     */
    @Override
    public boolean deleteUser() {
        // 예시: 현재 인증된 사용자의 정보를 받아 처리
        throw new UnsupportedOperationException("구현 필요");
    }

    /**
     * 로그아웃 메서드.
     * 사용자 세션 또는 인증 정보를 무효화.
     *
     * @return 로그아웃 성공 여부
     */
    @Override
    public boolean logout() {
        // 예시: 세션 관리 또는 인증 토큰 무효화
        throw new UnsupportedOperationException("구현 필요");
    }


    /**
     * 임시 비밀번호 생성 메서드.
     *
     * @return 랜덤으로 생성된 임시 비밀번호
     */
    private String generateTempPassword() {
        return "Temp1234!"; // 실제 구현에서는 난수 생성 로직 필요
    }
    

}

