//package TeamGoat.TripSupporter.Service;
//
//import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
//import TeamGoat.TripSupporter.Domain.Entity.User.User;
//import TeamGoat.TripSupporter.Repository.User.UserRepository;
//import TeamGoat.TripSupporter.Service.User.UserServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceImplTest {
//
//
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Test
//    public void register_t1() {
//        // Given: 유효한 User 객체 생성
//        UserDto userDto = UserDto.builder()
//                .userEmail("test@example.com")
//                .userPassword("1234")
//                .userNickname("nickname")
//                .userPhone("010-1234-5678")
//                .build();
//
//        // Mock 설정
//        Mockito.when(userRepository.existsByUserEmail(userDto.getUserEmail())).thenReturn(false);
//        Mockito.when(passwordEncoder.encode(userDto.getUserPassword())).thenReturn("encodedPassword");
//
//        // When: register 메소드를 호출
//        boolean result = userService.register(userDto);
//
//        // Then: userRepository.save가 한 번 호출되고, 결과가 true인지 검증
//        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
//        System.out.println("메소드 호출 여부 : " + result);
//        System.out.println("이메일 중복 여부 : " + userRepository.existsByUserEmail(userDto.getUserEmail()));
//        System.out.println("암호화 된 비밀번호 : " + passwordEncoder.encode(userDto.getUserPassword()));
//        assertTrue(result);
//    }
//
//    @Test
//    public void register_t2() {
//        // Given: 중복된 이메일을 가진 UserDto 객체
//        UserDto userDto = UserDto.builder()
//                .userEmail("duplicate@example.com")
//                .userPassword("1234")
//                .userNickname("nickname")
//                .userPhone("010-1234-5678")
//                .build();
//
//        // Mock 설정: 이메일 중복 여부를 true로 반환
//        Mockito.when(userRepository.existsByUserEmail(userDto.getUserEmail())).thenReturn(true);
//
//        // When & Then: 중복 이메일로 인해 예외가 발생하는지 검증
//        try {
//            userService.register(userDto);
//        } catch (IllegalArgumentException e) {
//            System.out.println("예외 발생 확인: " + e.getMessage());
//        }
//
//        // Mock 동작 확인 및 출력
//        boolean emailExists = userRepository.existsByUserEmail(userDto.getUserEmail());
//        System.out.println("이메일 중복 여부 : " + emailExists);
//
//        // save 메서드가 호출되지 않았는지 검증
//        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
//        System.out.println("userRepository.save 호출 여부: 호출되지 않음");
//    }
//
//
//    @Test
//    void login() {
//    }
//
//    @Test
//    void findId() {
//    }
//
//    @Test
//    void isEmailDuplicate() {
//    }
//
//    @Test
//    void findPassword() {
//    }
//
//    @Test
//    void isNicknameDuplicate() {
//    }
//
//    @Test
//    void deleteUser() {
//    }
//
//    @Test
//    void logout() {
//    }
//}