package TeamGoat.TripSupporter.Domain.Entity.User;

import TeamGoat.TripSupporter.Domain.Enum.UserRole;
import TeamGoat.TripSupporter.Domain.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_USER_PROFILE")
@Getter
@ToString
@Builder
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

    @ManyToOne  // User의 USER_NICKNAME fk
    @JoinColumn(name = "USER_NICKNAME", referencedColumnName = "USER_NICKNAME", foreignKey = @ForeignKey(name = "FK_USER_PROFILE_USER_NICKNAME"), nullable = false)
    private User userByNickname;
}
