package TeamGoat.TripSupporter.Service.User;

import TeamGoat.TripSupporter.Domain.Dto.Auth.AuthDto;
import TeamGoat.TripSupporter.Domain.Dto.Auth.TokenInfo;
import TeamGoat.TripSupporter.Domain.Dto.User.UserAndProfileDto;
import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 사용자 관련 비즈니스 로직을 정의하는 서비스 인터페이스.
 */
@Service
public interface UserService {

    /**
     * 회원 가입 메서드.
     *
     * @param userAndProfileDto 회원 가입에 필요한 사용자 정보
     * @return 회원 가입 성공 여부
     */
    AuthDto.LoginResponse register(UserAndProfileDto userAndProfileDto);

    public void deleteUser(String email);


    void updateUser(String email, UserDto updatedData);

    /**
     * 아이디(이메일) 찾기 메서드.
     *
     * @param phone 사용자 전화번호
     * @return 등록된 이메일 주소 (없을 경우 null)
     */
    List<String> findId(String phone);

    boolean isNicknameDuplicate(String nickname);

    /**
     * 비밀번호 찾기 메서드.
     *
     * @param email 사용자 이메일
     * @param phone 사용자 전화번호
     * @return 비밀번호 재설정 링크 또는 상태 메시지
     */
    void findPassword(String email, String phone);

    /**
     * 이메일 중복 확인 메서드.
     *
     * @param email 확인할 이메일
     * @return 이메일이 이미 존재하면 true, 아니면 false
     */
    boolean isEmailDuplicate(String email);

    /**
     * 비밀번호 변경 메서드
     * @param userId 사용자 Id
     * @param newPassword 새로운 비밀번호
     */
    void changePassword(Long userId, String currentPassword, String newPassword);



//    void updateUser(UserDto userDTO);
}
