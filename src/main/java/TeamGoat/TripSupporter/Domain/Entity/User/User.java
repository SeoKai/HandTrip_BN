package TeamGoat.TripSupporter.Domain.Entity.User;

import TeamGoat.TripSupporter.Domain.Entity.Bookmark.BookmarkPlanner;
import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import TeamGoat.TripSupporter.Domain.Enum.UserRole;
import TeamGoat.TripSupporter.Domain.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TBL_USER")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;    //pk

    @Column(name = "USER_EMAIL", nullable = false, unique = true)
    private String userEmail;   //실제 로그인할때 사용

    @Column(name = "USER_PASSWORD", nullable = false)
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE", nullable = false)
    private UserRole userRole = UserRole.USER;  //enum : USER, ADMIN

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STATUS", nullable = false)
    private UserStatus userStatus = UserStatus.ACTIVE;  //enum : ACTIVE, SUSPENDED, DEACTIVATED

    @Column(name = "USER_NICKNAME", nullable = false, unique = true)
    private String userNickname;

    @Column(name = "FAILED_LOGIN_ATTEMPTS", nullable = false)
    private int failedLoginAttempts = 0;

    @Column(name = "USER_PHONE")
    private String userPhone;

    @Column(name = "LOCKED_UNTIL")
    private LocalDateTime lockedUntil;

    @Column(name = "LAST_LOGIN")
    private LocalDateTime lastLogin;

    @Column(name = "PLANNER_CREATED_AT", updatable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime userCreatedAt;  //생성일자 - 수정불가

    @Column(name = "SNS_TYPE")
    private String snsType;

    @Column(name = "SNS_CONNECT_DATE")
    private LocalDateTime snsConnectDate;

//  중계테이블 bookmark와 1:M 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookmarkPlanner> bookmarkPlanner = new HashSet<>(); // 북마크한 플래너들

    @Builder
    public User(Long userId, String userEmail, String userPassword,
                UserRole userRole, UserStatus userStatus, String userNickname, int failedLoginAttempts,
                LocalDateTime lockedUntil, LocalDateTime lastLogin, LocalDateTime userCreatedAt,
                String snsType, LocalDateTime snsConnectDate) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.userNickname = userNickname;
        this.failedLoginAttempts = failedLoginAttempts;
        this.lockedUntil = lockedUntil;
        this.lastLogin = lastLogin;
        this.userCreatedAt = userCreatedAt;
        this.snsType = snsType;
        this.snsConnectDate = snsConnectDate;
    }

}
