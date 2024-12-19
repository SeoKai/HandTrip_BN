package TeamGoat.TripSupporter.Domain.Entity.User;

import TeamGoat.TripSupporter.Domain.Enum.UserRole;
import TeamGoat.TripSupporter.Domain.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_USER")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_EMAIL", nullable = false, unique = true)
    private String userEmail;   //실제 로그인할때 사용

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE", nullable = false)
    private UserRole userRole = UserRole.USER;  //enum : USER, ADMIN

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STATUS", nullable = false)
    private UserStatus userStatus = UserStatus.ACTIVE;  //enum : ACTIVE, SUSPENDED, DEACTIVATED

    @Column(name = "PLANNER_CREATED_AT", updatable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime userCreatedAt;  //생성일자 - 수정불가

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private UserProfile userProfile;

    @Builder
    public User(Long userId, String userEmail,String password, UserRole userRole, UserStatus userStatus, LocalDateTime userCreatedAt){
        this.userId = userId;
        this.userEmail = userEmail;
        this.password = password;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.userCreatedAt = userCreatedAt;
    }

    /**
     * 비밀번호 업데이트 메서드
     *
     * @param password 새로운 비밀번호
     */
    public void updateUser(String password){
        this.password = password;
    }

    /**
     * 양방향 연관 관계 설정 메서드
     *
     * UserProfile과 연관 관계를 설정하는 메서드로,
     * UserProfile 객체가 생성될 때 User 객체와의 관계를 설정합니다.
     *
     * @param userProfile UserProfile 객체
     */
    // 양방향 연관 관계 설정 메서드
    public void associateUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }



}
