package TeamGoat.TripSupporter.Domain.Entity.User;

import TeamGoat.TripSupporter.Domain.Enum.UserRole;
import TeamGoat.TripSupporter.Domain.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_USER")
@Getter
@ToString(exclude = "userProfile") // 순환 참조 방지
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
    @Column(name = "USER_ROLE")
    private UserRole userRole;  //enum : USER, ADMIN

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STATUS")
    private UserStatus userStatus;  //enum : ACTIVE, SUSPENDED, DEACTIVATED

    @Column(name = "PLANNER_CREATED_AT", updatable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime userCreatedAt;  //생성일자 - 수정불가

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)  // 외래키 관리하지 않음
    private UserProfile userProfile;

    @Builder
    public User(Long userId, String userEmail,String password, UserRole userRole, UserStatus userStatus, LocalDateTime userCreatedAt,UserProfile userProfile){
        this.userId = userId;
        this.userEmail = userEmail;
        this.password = password;
        if(userRole == null){
            this.userRole = UserRole.USER;  //enum : USER, ADMIN
        }
        if(userStatus == null){
            this.userStatus = UserStatus.ACTIVE;  //enum : USER, ADMIN
        }
        this.userCreatedAt = userCreatedAt;
        this.userProfile = userProfile;
    }

    /**
     * 비밀번호 업데이트 메서드
     *
     * @param password 새로운 비밀번호
     */
    public void updatePassword(String password){
        this.password = password;
    }

    /**
     * userCreatedAt 자동 설정
     */
    @PrePersist
    public void prePersist() {
        this.userCreatedAt = (this.userCreatedAt == null) ? LocalDateTime.now() : this.userCreatedAt;
    }



}
