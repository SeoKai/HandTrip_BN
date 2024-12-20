package TeamGoat.TripSupporter.Service.User;

import TeamGoat.TripSupporter.Domain.Dto.User.UserAndProfileDto;
import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Exception.User.UserException;
import TeamGoat.TripSupporter.Exception.UserNotFoundException;
import TeamGoat.TripSupporter.Mapper.User.UserMapper;
import TeamGoat.TripSupporter.Repository.User.UserRepository;
import TeamGoat.TripSupporter.Service.User.Util.RandomStringGenerator;
import TeamGoat.TripSupporter.Service.User.Util.UserServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 구현체.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserAndProfileDto userAndProfileDto) {
        try{
            // UserAndProfileDto에서 UserDto를 추출하여 user로 변환하고 유효성 검사
            User user = userMapper.toUserEntity(userAndProfileDto.getUserDto());
            UserServiceValidator.validateConvert(user);
            // password를 인코딩하여 업데이트
            user.updatePassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);
        }catch(Exception e){
            throw new UserException("회원가입 실패", e);
        }

    }

    public void updatePassword(Long userId , String password){
        try{
            // 사용자 id로 User정보를 불러와 새로운 password를 인코딩하여 update 후 저장
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자"));
            user.updatePassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }catch(Exception e){
            throw new UserException("비밀번호 변경 실패", e);
        }
    }

    public String findUserByKeyword(String keyword) {
        try{
            String response = null;

            if(UserServiceValidator.isEmailAddress(keyword)){
                // keyword email 형식이면 - email로 유저 엔티티를 찾음
                User user = userRepository.findByUserEmail(keyword)
                        .orElseThrow(()-> new UserNotFoundException("이메일에 해당하는 사용자가 존재하지 않습니다."));
                // 특수문자 2자리가 포함된 12자리 난수생성
                String newPassword = RandomStringGenerator.generateRandomString(12,2);
                // 생성된 비밀번호가 회원가입 시 비밀번호 조건에 맞는지 확인하는 메서드 추가 해야됨
                // UserServiceValidator.비밀번호조건유효성확인메서드()
                // 생성된 비밀번호를 암호화하여 저장
                user.updatePassword(passwordEncoder.encode(newPassword));
                // 생성된 비밀번호를 반환값에 저장
                response = newPassword;

            }else if(UserServiceValidator.isPhoneNumber(keyword)){
                // keyword가 휴대폰번호면 - 번호로 email reponse에 email 담음
                User user = userRepository.findByUserProfilePhoneNumber(keyword)
                        .orElseThrow(() -> new UserNotFoundException("휴대폰 번호에 해당하는 사용자가 존재하지 않습니다."));
                response = user.getUserEmail();
            }

            UserServiceValidator.validateKeyword(response);
            return response;

        }catch(Exception e){
            throw new UserException("회원정보 찾기 실패", e);

        }
    }

    public void deleteUser(Long userId) {
        try{
            // 사용자 id로 User를 찾아 존재하는지 확인
            User user = userRepository.findById(userId)
                   .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자"));
            // 삭제
            userRepository.delete(user);

        }catch(Exception e){
            throw new UserException("회원 탈퇴 실패", e);
        }

    }


}

