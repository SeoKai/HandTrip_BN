package TeamGoat.TripSupporter.Domain.Entity.Ai;

import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tbl_ai_user")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_ai_id")
    private Long userAiId;

    // User와의 ManyToOne 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // tbl_user의 user_id 외래 키 참조
    private User user;

    // Location과의 ManyToOne 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false) // tbl_location의 location_id 외래 키 참조
    private Location location;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Builder
    public AiUser(User user, Location location, Integer rating) {
        this.user = user;
        this.location = location;
        this.rating = rating;
    }
}
