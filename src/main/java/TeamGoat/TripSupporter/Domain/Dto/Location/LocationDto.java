package TeamGoat.TripSupporter.Domain.Dto.Location;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDto {

    private Long locationId; // 고유 ID
    private String placeId; // Google Places 고유 ID
    private String locationName; // 장소 이름
    private String description; // 장소 설명
    private Double latitude; // 위도
    private Double longitude; // 경도
    private Float googleRating; // Google 평점
    private Integer userRatingsTotal; // 총 리뷰 수
    private String placeImgUrl; // 장소 이미지 URL
    private String formattedAddress; // 주소
    private String openingHours; // 운영 시간
    private String website; // 웹사이트 URL
    private String phoneNumber; // 전화번호
    private String regionName; // 지역 이름
    private Set<String> tags; // 태그 이름 목록
}
