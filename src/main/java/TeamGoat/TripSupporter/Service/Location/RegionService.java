package TeamGoat.TripSupporter.Service.Location;

import TeamGoat.TripSupporter.Domain.Dto.Location.RegionDto;
import TeamGoat.TripSupporter.Mapper.Location.RegionMapper;
import TeamGoat.TripSupporter.Repository.Location.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionService {
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    public List<RegionDto> getAllRegions(){
        return regionRepository.findAll().stream().map(regionMapper :: toDto).collect(Collectors.toList());
    }

}
