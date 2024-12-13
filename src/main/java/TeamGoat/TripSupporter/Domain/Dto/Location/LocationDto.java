package TeamGoat.TripSupporter.Domain.Dto.Location;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LocationDto {
    private Long locationId;          // 고유 ID

    private String placeId;          // Google Places 고유 ID

    private String locationName;     // 장소 이름

    private String description;      // 장소 설명

    private double latitude;         // 위도

    private double longitude;        // 경도

    private String address;          // 주소

    private float googleRating;      // 구글 평점

    private String placeImgUrl;      // 장소 이미지 URL

    private Long regionId;           // 관광지가 속한 지역의 ID
}
