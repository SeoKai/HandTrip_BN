//package TeamGoat.TripSupporter.Controller;
//
//
//import TeamGoat.TripSupporter.Controller.User.UserController;
//import TeamGoat.TripSupporter.Domain.Dto.User.UserDto;
//import TeamGoat.TripSupporter.Service.User.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UserController.class)
//@AutoConfigureMockMvc(addFilters = false)
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    @Test
//    @DisplayName("회원가입 성공 테스트")
//    public void joinUser_Success_WithCsrf() throws Exception {
//        // Given
//        UserDto userDto = UserDto.builder()
//                .userEmail("example@example.com")
//                .userPassword("1234")
//                .userNickname("이재용")
//                .userPhone("010-5052-5656")
//                .build();
//
//        Mockito.when(userService.register(any(UserDto.class))).thenReturn(true);
//
//        String json = new ObjectMapper().writeValueAsString(userDto);
//
//        // When & Then
//        mockMvc.perform(post("/user/join")
//                        .content(json)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(content().string("회원가입이 성공적으로 완료되었습니다."));
//    }
//
//
//
//    @Test
//    @DisplayName("회원가입 실패 테스트")
//    void joinUser_Failure() throws Exception {
//        // Given: 회원가입에 사용할 UserDto 생성
//        UserDto userDto = UserDto.builder()
//                .userEmail("example@example.com")
//                .userPassword("short")
//                .userNickname("이재용")
//                .userPhone("010-5052-5656")
//                .build();
//
//        // When: 서비스의 register 메서드에서 예외가 발생한다고 가정
//        Mockito.doThrow(new IllegalArgumentException("회원가입 실패: 비밀번호는 8자 이상이어야 합니다."))
//                .when(userService).register(any(UserDto.class));
//
//        // Then: MockMvc로 POST 요청을 수행하고 예외 메시지 확인
//        mockMvc.perform(post("/join")
//                        .content(objectMapper.writeValueAsString(userDto))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("회원가입 실패: 비밀번호는 8자 이상이어야 합니다."));
//    }
//
//    // 나머지 테스트 메서드는 이와 유사하게 작성하시면 됩니다
//
//
//
//@Test
//    void loginUser() {
//    }
//
//    @Test
//    void getUserProfile() {
//    }
//
//    @Test
//    void testGetUserProfile() {
//    }
//
//    @Test
//    void checkEmailDuplication() {
//    }
//
//    @Test
//    void checkNicknameDuplication() {
//    }
//
//    @Test
//    void findPassword() {
//    }
//
//    @Test
//    void logout() {
//    }
//}