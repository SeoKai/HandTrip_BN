package TeamGoat.TripSupporter.Domain.Entity.Location;

import TeamGoat.TripSupporter.Domain.Entity.Favorite.UserLocationFavorite;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_location")
@Getter
@ToString(exclude = {"region", "tags", "favorites"}) // 순환 참조 방지
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId; // 고유 ID

    @Column(name = "place_id", nullable = false, unique = true)
    private String placeId; // Google Places 고유 ID

    @Column(name = "location_name", nullable = false)
    private String locationName; // 장소 이름

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 장소 설명

    @Column(name = "latitude", nullable = false)
    private Double latitude; // 위도

    @Column(name = "longitude", nullable = false)
    private Double longitude; // 경도

    @Column(name = "google_rating")
    private Float googleRating; // Google 평점

    @Column(name = "user_ratings_total")
    private Integer userRatingsTotal; // 총 사용자 리뷰 수

    @Column(name = "place_img_url", columnDefinition = "TEXT")
    private String placeImgUrl; // 장소 이미지 URL

    @Column(name = "formatted_address")
    private String formattedAddress; // 형식화된 주소

    @Column(name = "opening_hours", columnDefinition = "TEXT")
    private String openingHours; // 운영 시간

    @Column(name = "website")
    private String website; // 웹사이트 URL

    @Column(name = "phone_number")
    private String phoneNumber; // 전화번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false) // 외래키 매핑
    private Region region; // 소속 지역

    @ManyToMany
    @JoinTable(
            name = "tbl_location_tag",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags; // 연관 태그

    // 즐겨 찾기
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLocationFavorite> favorites = new ArrayList<>();

    @Builder
    public Location(Long locationId, String placeId, String locationName, String description, Double latitude, Double longitude,
                    Float googleRating, Integer userRatingsTotal, String placeImgUrl, String formattedAddress,
                    String openingHours, String website, String phoneNumber, Region region, Set<Tag> tags) {
        this.locationId = locationId;
        this.placeId = placeId;
        this.locationName = locationName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.googleRating = googleRating;
        this.userRatingsTotal = userRatingsTotal;
        this.placeImgUrl = placeImgUrl;
        this.formattedAddress = formattedAddress;
        this.openingHours = openingHours;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.region = region;
        this.tags = tags;
    }
}
