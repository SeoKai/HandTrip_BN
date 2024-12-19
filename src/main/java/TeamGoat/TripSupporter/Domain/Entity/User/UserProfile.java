package TeamGoat.TripSupporter.Domain.Entity.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_USER_PROFILE")
@Getter
@ToString(exclude = "user") // 순환 참조 방지
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROFILE_ID")
    private Long userProfileId;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname;

    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @Column(name = "PROFILE_IMAGE_URL", length = 2083)
    private String profileImageUrl;

    @Column(name = "USER_BIO", columnDefinition = "TEXT")
    private String userBio; // 프로필 자기소개글

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, referencedColumnName = "USER_ID", foreignKey = @ForeignKey(name = "FK_USER_PROFILE_USER_ID"))
    private User user;

    @Builder
    public UserProfile(Long userProfileId,String nickname, String phoneNumber,String profileImageUrl,String userBio,User user) {
        this.userProfileId = userProfileId;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.userBio = userBio;
        this.user = user;
        if (user != null && user.getUserProfile() != this) {
            user.associateUserProfile(this); // User와 UserProfile의 관계 설정
        }
    }


    /**
     * 양방향 연관 관계 설정 메서드
     *
     * UserProfile과 User 객체를 양방향으로 연결하는 메서드입니다.
     * 이 메서드는 UserProfile 객체가 생성될 때 자동으로 호출되어야 합니다.
     *
     * @param user User 객체
     */
    // 양방향 연관 관계 설정 메서드
    public void associateUser(User user) {
        this.user = user;
        if (user.getUserProfile() != this) {
            user.associateUserProfile(this); // UserProfile이 User에게 연결되도록 설정
        }
    }

    /**
     * 프로필 정보를 업데이트하는 메서드
     *
     * @param nickname 새로운 닉네임
     * @param phoneNumber 새로운 전화번호
     * @param userBio 새로운 자기소개글
     */
    // 프로필 정보 업데이트 메서드
    public void updateProfile(String nickname, String phoneNumber, String userBio) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.userBio = userBio;
    }
}
