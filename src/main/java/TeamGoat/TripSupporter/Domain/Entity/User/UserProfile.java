package TeamGoat.TripSupporter.Domain.Entity.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_USER_PROFILE")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class UserProfile {
    @Id
    @Column(name = "USER_ID")
    private Long userId;

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
    public UserProfile(Long userId, String userNickname, String profileImageUrl, String userBio, User user, User userByNickname) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.profileImageUrl = profileImageUrl;
        this.userBio = userBio;
        this.user = user;
    }
}