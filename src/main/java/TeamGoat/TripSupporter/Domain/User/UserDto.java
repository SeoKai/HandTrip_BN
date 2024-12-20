package TeamGoat.TripSupporter.Domain.User;

import TeamGoat.TripSupporter.Domain.Enum.UserRole;
import TeamGoat.TripSupporter.Domain.Enum.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {

    @NotBlank
    private Long userId;

    @NotBlank(message = "이메일은 필수 입력 값입니다")
    private String userEmail;

    @NotBlank(message = "비밀번호를 입력 해 주세요")
    private String userPassword;

    @NotBlank(message = " 필수 입력 값입니다")
    private UserRole userRole;  // enum : user,admin

    private UserStatus userStatus; // enum : ACTIVE, SUSPENDED, DEACTIVATED

    @NotBlank(message = "휴대폰 번호는 필수 입력 값입니다")
    private String userPhone;

    //OAUTH2 CLIENT
    private String provider;
    private String providerId;


//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
//    private LocalDateTime lockedUntil; //계정잠금 해제 날짜
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss"  )
//    private LocalDateTime lastLogin;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss"  )
//    private LocalDateTime userCreatedAt;// 계정생성일


//    private String snsType;  // sns 타입 (ex: facebook, google)
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss"  )
//    private LocalDateTime snsConnectDate; // sns 연결날짜

}
