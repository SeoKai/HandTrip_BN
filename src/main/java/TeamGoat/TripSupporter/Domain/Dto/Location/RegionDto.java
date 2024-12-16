package TeamGoat.TripSupporter.Domain.Dto.Location;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegionDto {

    private Long regionId;          // 지역 ID
    private String regionName;      // 지역 이름 (예: Tokyo, Osaka)
    private String description;     // 지역 설명

    private List<LocationDto> locations; // 해당 지역의 관광지 목록
}
