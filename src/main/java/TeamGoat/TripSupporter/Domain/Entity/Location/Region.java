package TeamGoat.TripSupporter.Domain.Entity.Location;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_region")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long regionId; // 고유 ID

    @Column(name = "region_name", nullable = false, unique = true)
    private String regionName; // 지역 이름

    @Builder
    public Region(Long regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;
    }
}
