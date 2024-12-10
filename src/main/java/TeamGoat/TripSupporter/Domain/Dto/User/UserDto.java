package TeamGoat.TripSupporter.Domain.Dto.User;

import TeamGoat.TripSupporter.Domain.Enum.UserRole;
import TeamGoat.TripSupporter.Domain.Enum.UserStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
<<<<<<< HEAD
@ToString
=======
>>>>>>> eb2400e6ef8985be4db8b9249b3db945c5ea5104
public class UserDto {

    private Long userId;

    private String userEmail;

    private String userPassword;

    private UserRole userRole;  // enum : user,admin

    private UserStatus userStatus; // enum : ACTIVE, SUSPENDED, DEACTIVATED

    private String userNickname;

    private String userPhone;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime lockedUntil; //계정잠금 해제 날짜

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss"  )
    private LocalDateTime lastLogin;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss"  )
    private LocalDateTime userCreatedAt;// 계정생성일

    private String snsType;  // sns 타입 (ex: facebook, google)

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss"  )
    private LocalDateTime snsConnectDate; // sns 연결날짜

}
