
//package TeamGoat.TripSupporter.Controller;
//
//import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
//import TeamGoat.TripSupporter.Service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/user")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//
//    @PostMapping("/join")
//    public ResponseEntity<?> joinUser(@RequestBody UserDto userDto) {
//        try {
//            userService.createUser(userDto);
//            return ResponseEntity.ok().body("회원가입이 성공적으로 완료되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
//        try {
//            UserDto loginUser = userService.loginUser(userDto.getEmail(), userDto.getPassword());
//            return ResponseEntity.ok(loginUser);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/profile/{id}")
//    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
//        try {
//            UserDto userProfile = userService.getUserProfile(id);
//            return ResponseEntity.ok(userProfile);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<?> updateUser(@RequestBody UserDto userDTO) {
//        try {
//            userService.updateUser(userDTO);
//            return ResponseEntity.ok("회원 정보가 성공적으로 업데이트되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//}

