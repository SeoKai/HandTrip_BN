package TeamGoat.TripSupporter.Mapper;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LocationMapper {
    public LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .locationId(location.getLocationId())
                .placeId(location.getPlaceId())
                .locationName(location.getLocationName())
                .description(location.getDescription())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .googleRating(location.getGoogleRating())
                .userRatingsTotal(location.getUserRatingsTotal())
                .placeImgUrl(location.getPlaceImgUrl())
                .formattedAddress(location.getFormattedAddress())
                .openingHours(location.getOpeningHours())
                .website(location.getWebsite())
                .phoneNumber(location.getPhoneNumber())
                .regionName(location.getRegion().getRegionName()) // 지역 이름
                .tags(location.getTags().stream()
                        .map(tag -> tag.getTagName()) // 태그 이름만 추출
                        .collect(Collectors.toSet()))
                .build();
    }
}
