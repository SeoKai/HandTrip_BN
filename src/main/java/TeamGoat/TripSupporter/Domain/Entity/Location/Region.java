package TeamGoat.TripSupporter.Domain.Entity.Location;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_region") // 테이블 이름 매핑
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 보호
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    @Column(name = "region_id") // 테이블 컬럼 이름 매핑
    private Long regionId; // 고유 ID

    @Column(name = "region_name", nullable = false, unique = true, length = 50) // NOT NULL, UNIQUE
    private String regionName; // 지역 이름

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 지역 설명

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> locations = new ArrayList<>(); // 해당 지역의 관광지 목록

    @Builder
    public Region(Long regionId, String regionName, String description) {
        this.regionId = regionId;
        this.regionName = regionName;
        this.description = description;
    }
}
