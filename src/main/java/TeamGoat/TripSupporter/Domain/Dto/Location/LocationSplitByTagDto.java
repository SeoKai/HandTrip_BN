package TeamGoat.TripSupporter.Domain.Dto.Location;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LocationSplitByTagDto {

    private List<LocationResponseDto> locationResponseDtoIncludeTag;
    private List<LocationResponseDto> locationResponseDtoExcludeTag;

}
