package TeamGoat.TripSupporter.Domain.Entity.User;

import TeamGoat.TripSupporter.Domain.Entity.Favorite.UserLocationFavorite;
import TeamGoat.TripSupporter.Domain.Enum.UserRole;
import TeamGoat.TripSupporter.Domain.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TBL_USER")
@Getter
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

    @Column(name = "USER_PHONE")
    private String userPhone;

    @Column(name = "PLANNER_CREATED_AT", updatable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime userCreatedAt;  //생성일자 - 수정불가

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private UserProfile userProfile;

    @Column(name = "PROVIDER")
    private String provider;

    @Column(name = "PROVIDER_ID")
    private String providerId;

    // 즐겨찾기
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLocationFavorite> favorites = new ArrayList<>();

    @Builder
    public User(Long userId, String userEmail, String userPassword, String userPhone,
                UserRole userRole, UserStatus userStatus,
                LocalDateTime userCreatedAt,
                String provider, String providerId) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.userCreatedAt = userCreatedAt;
        this.provider = provider;
        this.providerId = providerId;
//        this.snsType = snsType;
//        this.snsConnectDate = snsConnectDate;
    }

    @PrePersist
    protected void onCreate() {
        this.userCreatedAt = LocalDateTime.now();
    }

    public void updatePassword(String encodedPassword) {
        this.userPassword = encodedPassword;
    }

    public void updatePhone(String newPhone) {
        this.userPhone = newPhone;
    }

}
