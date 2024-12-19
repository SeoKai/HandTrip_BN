package TeamGoat.TripSupporter.Domain.Dto.User;

import TeamGoat.TripSupporter.Domain.Enum.UserRole;
import TeamGoat.TripSupporter.Domain.Enum.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {

    private Long userId;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "이메일 형식이 유효하지 않습니다.")
    private String userEmail;   // 실제 로그인할 때 사용

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    private UserRole userRole;

    private UserStatus userStatus;

    private LocalDateTime userCreatedAt;

}
