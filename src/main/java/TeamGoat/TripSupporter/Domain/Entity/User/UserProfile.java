package TeamGoat.TripSupporter.Domain.Entity.User;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_USER_PROFILE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_PROFILE_ID")
    private Long userProfileId;

    @Column(name = "USER_NICKNAME", nullable = false)
    private String userNickname;

    @Column(name = "PROFILE_IMAGE_URL", length = 2083)
    private String profileImageUrl;

    @Column(name = "USER_BIO", columnDefinition = "TEXT")
    private String userBio; // 프로필 자기소개글

    @OneToOne   // User의 userId fk
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", foreignKey = @ForeignKey(name = "FK_USER_PROFILE_USER_ID"), nullable = false)
    private User user;

    @Builder
    public UserProfile(Long userProfileId, String userNickname, String profileImageUrl, String userBio, User user) {
        this.userProfileId = userProfileId;
        this.userNickname = userNickname;
        this.profileImageUrl = profileImageUrl;
        this.userBio = userBio;
        this.user = user;
    }

    public void updateUserProfileImgUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
