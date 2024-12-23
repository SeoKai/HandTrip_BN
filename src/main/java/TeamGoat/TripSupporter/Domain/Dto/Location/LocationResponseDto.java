package TeamGoat.TripSupporter.Domain.Dto.Location;

import lombok.*;

import java.util.Set;

// 조회 시 클라이언트에 반환할 데이터 가공용 DTO

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LocationResponseDto {
    private Long locationId;          // 관광지 고유 ID
    private String locationName;      // 장소 이름
    private String description;       // 장소 설명
    private Float googleRating;       // Google 평점
    private Integer userRatingsTotal; // 총 리뷰 수
    private String placeImgUrl;       // 장소 이미지 URL
    private String formattedAddress;  // 주소
    private String openingHours;      // 운영 시간
    private String website;           // 웹사이트 URL
    private String phoneNumber;       // 전화번호
    private String regionName;        // 지역 이름
    private Set<String> tags;         // 태그 이름 목록
    private Double distance;          // 중심좌표로부터 떨어진 거리

    // `distance` 필드를 뺀 생성자 추가
    public LocationResponseDto(Long locationId, String locationName, String description,
                               Float googleRating, Integer userRatingsTotal, String placeImgUrl,
                               String formattedAddress, String openingHours, String website,
                               String phoneNumber, String regionName, Set<String> tags) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.description = description;
        this.googleRating = googleRating;
        this.userRatingsTotal = userRatingsTotal;
        this.placeImgUrl = placeImgUrl;
        this.formattedAddress = formattedAddress;
        this.openingHours = openingHours;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.regionName = regionName;
        this.tags = tags;
    }
}