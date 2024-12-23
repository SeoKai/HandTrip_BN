package TeamGoat.TripSupporter.Domain.Entity.Location;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_location_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location; // 관련된 Location

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag; // 관련된 Tag

    @Builder
    public LocationTag(Long id, Location location, Tag tag) {
        this.id = id;
        this.location = location;
        this.tag = tag;
    }
}
