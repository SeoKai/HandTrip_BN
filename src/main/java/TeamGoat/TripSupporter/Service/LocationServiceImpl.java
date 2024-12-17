package TeamGoat.TripSupporter.Service;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Mapper.LocationMapper;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    // 생성자 주입
    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    // regionId를 기반으로 장소 조회
    public List<LocationDto> getLocationsByRegion(Long regionId) {
        List<Location> locations = locationRepository.findByRegionRegionId(regionId); // Repository 메서드 호출
        return locations.stream()
                .map(locationMapper::toLocationDto) // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }
}
