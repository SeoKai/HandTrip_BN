package TeamGoat.TripSupporter.Service;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Mapper.LocationMapper;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    // 생성자 주입
    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public List<LocationDto> getLocationsByRegion(String regionName) {
        List<Location> locations = locationRepository.findByRegionRegionName(regionName);
        return locations.stream()
                .map(locationMapper::toDto) // 인스턴스 메서드 참조
                .collect(Collectors.toList());
    }
}
