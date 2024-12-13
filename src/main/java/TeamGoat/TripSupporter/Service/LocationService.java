package TeamGoat.TripSupporter.Service;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
import TeamGoat.TripSupporter.Repository.LocationRepository;
import TeamGoat.TripSupporter.Repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
// final로 선언된 필드나 @NonNull이 붙은 필드에 대해 생성자를 자동으로 생성해주는 기능 제공
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final RegionRepository regionRepository;

    public LocationDto createLocation(LocationDto locationDto) {
        Region region = regionRepository.findById(locationDto.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Region not found"));

        Location location = Location.builder()
                .placeId(locationDto.getPlaceId())
                .locationName(locationDto.getLocationName())
                .description(locationDto.getDescription())
                .latitude(locationDto.getLatitude())
                .longitude(locationDto.getLongitude())
                .address(locationDto.getAddress())
                .googleRating(locationDto.getGoogleRating())
                .placeImgUrl(locationDto.getPlaceImgUrl())
                .region(region)
                .build();

        Location savedLocation = locationRepository.save(location);
        return toDto(savedLocation);
    }

    private LocationDto toDto(Location location) {
        return LocationDto.builder()
                .locationId(location.getLocationId())
                .placeId(location.getPlaceId())
                .locationName(location.getLocationName())
                .description(location.getDescription())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .address(location.getAddress())
                .googleRating(location.getGoogleRating())
                .placeImgUrl(location.getPlaceImgUrl())
                .regionId(location.getRegion().getRegionId())
                .build();
    }
}
