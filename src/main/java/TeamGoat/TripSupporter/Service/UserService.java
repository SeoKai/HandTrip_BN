package TeamGoat.TripSupporter.Service;

import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
import org.springframework.stereotype.Service;

/**
 * 사용자 관련 비즈니스 로직을 정의하는 서비스 인터페이스.
 */
@Service
public interface UserService {

    /**
     * 회원 가입 메서드.
     *
     * @param userDto 회원 가입에 필요한 사용자 정보
     * @return 회원 가입 성공 여부
     */
    boolean register(UserDto userDto);

    /**
     * 로그인 메서드.
     *
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @return 로그인 성공 여부
     */
    boolean login(String email, String password);

    /**
     * 아이디(이메일) 찾기 메서드.
     *
     * @param phone 사용자 전화번호
     * @return 등록된 이메일 주소 (없을 경우 null)
     */
    String findId(String phone);

    /**
     * 비밀번호 찾기 메서드.
     *
     * @param email 사용자 이메일
     * @param phone 사용자 전화번호
     * @return 비밀번호 재설정 링크 또는 상태 메시지
     */
    String findPassword(String email, String phone);

    /**
     * 이메일 중복 확인 메서드.
     *
     * @param email 확인할 이메일
     * @return 이메일이 이미 존재하면 true, 아니면 false
     */
    boolean isEmailDuplicate(String email);

    /**
     * 닉네임 중복 확인 메서드.
     *
     * @param nickname 확인할 닉네임
     * @return 닉네임이 이미 존재하면 true, 아니면 false
     */
    boolean isNicknameDuplicate(String nickname);

    /**
     * 회원 탈퇴 메서드.
     *
     * @return 탈퇴 성공 여부
     */
    boolean deleteUser();

    /**
     * 로그아웃 메서드.
     *
     * @return 로그아웃 성공 여부
     */
    boolean logout();



//    void updateUser(UserDto userDTO);
}
