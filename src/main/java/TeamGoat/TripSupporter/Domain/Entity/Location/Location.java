package TeamGoat.TripSupporter.Domain.Entity.Location;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_location")
@Getter
@ToString
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId; // 위치 ID

    @Column(name = "name", length = 100)
    private String name; // 위치 이름

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 위치 설명

    @Column(name = "latitude", precision = 11, scale = 8)
    private BigDecimal latitude; // 위도 11자리표시, 소수점이하는 8자리

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude; // 경도 11자리표시, 소수점이하는 8자리

    @Column(name = "location_created_at")
    private LocalDateTime locationCreatedAt; // 위치 생성 시각

    @Column(name = "location_updated_at")
    private LocalDateTime locationUpdatedAt; // 위치 수정 시각

    @Column(name = "image_url", length = 255)
    private String imageUrl; // 이미지 URL

}
