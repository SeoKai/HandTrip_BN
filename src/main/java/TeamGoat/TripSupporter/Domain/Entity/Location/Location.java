package TeamGoat.TripSupporter.Domain.Entity.Location;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_location")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId; // 고유 ID

    @Column(name = "place_id", nullable = false, unique = true)
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
    private float googleRating; // 평점

    @Column(name = "place_img_url", columnDefinition = "TEXT")
    private String placeImgUrl; // 장소 이미지 URL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false) // 외래키 매핑
    private Region region; // 해당 관광지가 속한 지역

    @Builder
    public Location(Long locationId, String placeId, String locationName, String description, double latitude, double longitude, String address, float googleRating, String placeImgUrl, Region region) {
        this.locationId = locationId;
        this.placeId = placeId;
        this.locationName = locationName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.googleRating = googleRating;
        this.placeImgUrl = placeImgUrl;
        this.region = region;
    }
}
