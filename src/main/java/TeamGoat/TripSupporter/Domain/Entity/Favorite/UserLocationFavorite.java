package TeamGoat.TripSupporter.Domain.Entity.Favorite;

import TeamGoat.TripSupporter.Domain.Entity.Location.Tag;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Table(name = "tbl_user_location_favorite")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLocationFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public UserLocationFavorite(Long id, Location location, User user) {
        this.id = id;
        this.location = location;
        this.user = user;
    }

    @Builder
    public UserLocationFavorite(User user, Location location){
        this.user = user;
        this.location = location;
    }

}