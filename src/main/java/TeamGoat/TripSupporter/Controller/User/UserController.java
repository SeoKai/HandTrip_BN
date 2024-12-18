

package TeamGoat.TripSupporter.Controller.User;


import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
import TeamGoat.TripSupporter.Service.User.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // restController 사용으로 ResponseBody 와 RequestBody 함께 사용 (보통 RESTfull API 에서는 restController 사용)
@RequestMapping("/user")
@Validated
// Controller, Service, Repository, Validator, Converter, and ProceedingJoinPoint에 @Valid, @NotNull, @Min, @Max, @Pattern, @Size, @Email, @Past, @Future, @PastOrPresent, @NotNullOrEmpty, @Positive, @PositiveOrZero, @Negative, @NegativeOrZero, @DecimalMin, @DecimalMax, @Digits, @FutureOrPresent, @NotNullOrBlank, @Size(min =)
//@RequiredArgsConstructor
public class UserController {

    //@ControllerAdvice 사용 생각 . . .
    //@ControllerAdvice란 애플리케이션 전역에서 컨트롤러의 공통적인 예외 처리, 데이터 바인딩, 모델 객체 처리를 담당하는 기능을 제공

    // UserService의 구현체를 스프링 컨테이너에서 주입받음
    @Autowired
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 요청 처리
     * @param userDto 회원가입에 필요한 사용자 정보
     * @return 성공 메시지 또는 에러 메시지
     */
    @PostMapping("/join")
    public ResponseEntity<?> joinUser(@Valid @RequestBody UserDto userDto) {
        try {
            userService.register(userDto); // UserService를 호출하여 회원 생성 로직 처리
            return ResponseEntity.ok().body("회원가입이 완료 되었습니다");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 로그인 요청 처리
     * @param userDto 로그인에 필요한 사용자 정보 (이메일과 비밀번호)
     * @return 로그인 성공 시 사용자 정보 또는 에러 메시지
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserDto userDto) {
        try {
            boolean loginUser = userService.login(userDto.getUserEmail(), userDto.getUserPassword());
            return ResponseEntity.ok(loginUser); // 로그인 성공 시 사용자 정보를 반환
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    /**
//     * 사용자 프로필 조회 요청 처리
//     * @param userDto
//     * @return 사용자 프로필 정보 또는 에러 메시지
//     */
//    @GetMapping("/myprofile")
//    public ResponseEntity<?> getUserProfile(@RequestBody UserDto userDto) {
//        try {
//            String userProfile = userService.findId(String.valueOf(userDto)); // ID를 기준으로 사용자 프로필 조회
//            return ResponseEntity.ok(userProfile);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
// @GetMapping("/myprofile/update")
//    public ResponseEntity<?> getUserProfile(@PathVariable String email) {
//        try {
//            String userProfile = userService.findId(email); // ID를 기준으로 사용자 프로필 조회
//            return ResponseEntity.ok(userProfile);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    /**
     * 사용자 정보 업데이트 요청 처리
     * @param userDTO 업데이트할 사용자 정보
     * @return 성공 메시지 또는 에러 메시지
     */
//    @PatchMapping("/update")
//    public ResponseEntity<?> updateUser(@RequestBody UserDto userDTO) {
//        try {
//            userService.updateUser(userDTO); // 사용자 정보 업데이트 처리
//            return ResponseEntity.ok("회원 정보가 성공적으로 업데이트되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    /**
     * 이메일 중복 체크 요청 처리
     * @param email 중복 체크할 이메일
     * @return 이메일 사용 가능 여부 메시지
     */
    @PostMapping("/update/email/check-duplication")
    public ResponseEntity<?> checkEmailDuplication(@Valid @RequestBody String email) {
        try {
            boolean isDuplicate = userService.isEmailDuplicate(email); // 이메일 중복 여부 확인
            return ResponseEntity.ok().body(isDuplicate
                    ? "이미 사용 중인 이메일입니다."
                    : "사용 가능한 이메일입니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 닉네임 중복 체크 요청 처리
     * @param nickname 중복 체크할 닉네임
     * @return 닉네임 사용 가능 여부 메시지
     */
    @PostMapping("/update/nickname/check-duplication")
    public ResponseEntity<?> checkNicknameDuplication(@Valid @RequestBody String nickname) {
        try {
            boolean isDuplicate = userService.isNicknameDuplicate(nickname); // 닉네임 중복 여부 확인
            return ResponseEntity.ok().body(isDuplicate
                    ? "이미 사용 중인 닉네임입니다."
                    : "사용 가능한 닉네임입니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 비밀번호 찾기 요청 처리
     * @param userDto 사용자 이메일과 전화번호 정보
     * @return 임시 비밀번호 또는 에러 메시지
     */
    @PostMapping("/findpw")
    public ResponseEntity<?> findPassword(@Valid @RequestBody UserDto userDto) {
        try {
            String tempPassword = userService.findPassword(userDto.getUserEmail(), userDto.getUserPhone());
            return ResponseEntity.ok().body("임시 비밀번호가 발급되었습니다: " + tempPassword);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 로그아웃 요청 처리
     * @param email 로그아웃할 사용자 이메일
     * @return 성공 메시지 또는 에러 메시지
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String email) {
        try {
            userService.logout(); // 로그아웃 처리
            return ResponseEntity.ok().body("로그아웃이 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

