package TeamGoat.TripSupporter.Service;


import TeamGoat.TripSupporter.Domain.Dto.Location.RegionDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
import TeamGoat.TripSupporter.Repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public Region createRegion(RegionDto regionDto) {
        Region region = Region.builder()
                .regionName(regionDto.getRegionName())
                .description(regionDto.getDescription())
                .build();
        return regionRepository.save(region);
    }
}
