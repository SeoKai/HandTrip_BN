package TeamGoat.TripSupporter.Mapper.Location;

import TeamGoat.TripSupporter.Domain.Dto.Location.RegionDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.TagDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
import TeamGoat.TripSupporter.Domain.Entity.Location.Tag;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper {
    public RegionDto toDto(Region region) {
        return RegionDto.builder()
                .regionId(region.getRegionId())
                .regionName(region.getRegionName())
                .build();
    }
}
