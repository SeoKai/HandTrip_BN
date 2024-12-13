package TeamGoat.TripSupporter.Domain.Dto.Location;

import lombok.*;

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
    private String address;           // 주소
    private float googleRating;       // 구글 평점
    private String placeImgUrl;       // 장소 이미지 URL
    private String regionName;        // 지역 이름 (조회를 위해 추가)
}
