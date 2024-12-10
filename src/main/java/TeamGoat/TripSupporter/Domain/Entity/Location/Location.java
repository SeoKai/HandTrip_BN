package TeamGoat.TripSupporter.Domain.Entity.Location;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "tbl_location")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    @Column(name = "location_id") // 테이블 컬럼 이름 매핑
    private int locationId; // 고유 ID

    @Column(name = "place_id", nullable = false, unique = true) // NOT NULL, UNIQUE
    private String placeId; // Google Places 고유 ID

    @Column(name = "location_name")
    private String locationName; // 장소 이름

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 장소 설명

    @Column(name = "latitude")
    private double latitude; // 위도

    @Column(name = "longitude")
    private double longitude; // 경도

    @Column(name = "address")
    private String address; // 주소

    @Column(name = "google_rating")
    private float googleRating; // 평점 (0.0~5.0)

    @Column(name = "types")
    private String types; // 장소 유형

    @Column(name = "place_img_url", columnDefinition = "TEXT")
    private String placeImgUrl; // 장소 이미지 URL

    @Builder
    public Location(int locationId, String placeId, String locationName, String description, double latitude, double longitude, String address, float googleRating, String types, String placeImgUrl) {
        this.locationId = locationId;
        this.placeId = placeId;
        this.locationName = locationName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.googleRating = googleRating;
        this.types = types;
        this.placeImgUrl = placeImgUrl;
    }
}
