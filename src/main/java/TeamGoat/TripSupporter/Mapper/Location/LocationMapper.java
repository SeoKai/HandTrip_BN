package TeamGoat.TripSupporter.Mapper.Location;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationWithDistanceDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
import TeamGoat.TripSupporter.Domain.Entity.Location.Tag;
import TeamGoat.TripSupporter.Service.Location.Util.PhotoUrlGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
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
                .placeImgUrl(PhotoUrlGenerator.generatePhotoUrl(location.getPlaceImgUrl())) // 호출
                .formattedAddress(location.getFormattedAddress())
                .openingHours(location.getOpeningHours())
                .website(location.getWebsite())
                .phoneNumber(location.getPhoneNumber())
                .regionName(location.getRegion().getRegionName())
                .tags(location.getTags().stream()
                        .map(tag -> tag.getTagName())
                        .collect(Collectors.toSet()))
                .build();
    }

    public LocationResponseDto toResponseDto(Location location) {
        return LocationResponseDto.builder()
                .locationId(location.getLocationId())
                .locationName(location.getLocationName())
                .description(location.getDescription())
                .googleRating(location.getGoogleRating())
                .userRatingsTotal(location.getUserRatingsTotal())
                .placeImgUrl(PhotoUrlGenerator.generatePhotoUrl(location.getPlaceImgUrl())) // 동적 URL 생성
                .formattedAddress(location.getFormattedAddress())
                .openingHours(location.getOpeningHours())
                .website(location.getWebsite())
                .phoneNumber(location.getPhoneNumber())
                .regionName(location.getRegion().getRegionName())
                .tags(location.getTags().stream()
                        .map(Tag::getTagName)
                        .collect(Collectors.toSet()))
                .build();
    }

    public LocationResponseDto toResponseDtoWithDistance(LocationWithDistanceDto locationWithDistanceDto){
        Location location = locationWithDistanceDto.getLocation();
        Double distance = locationWithDistanceDto.getDistance();

        return LocationResponseDto.builder()
                .locationId(location.getLocationId())
                .locationName(location.getLocationName())
                .description(location.getDescription())
                .googleRating(location.getGoogleRating())
                .userRatingsTotal(location.getUserRatingsTotal())
                .placeImgUrl(PhotoUrlGenerator.generatePhotoUrl(location.getPlaceImgUrl()))
                .formattedAddress(location.getFormattedAddress())
                .openingHours(location.getOpeningHours())
                .website(location.getWebsite())
                .phoneNumber(location.getPhoneNumber())
                .regionName(location.getRegion().getRegionName())
                .tags(location.getTags().stream()
                        .map(tag -> tag.getTagName()) // 태그 이름만 추출
                        .collect(Collectors.toSet()))
                .distance(distance)
                .build();
    }
    public Location toEntity(LocationDto locationDto, Region region, Set<Tag> tags) {
        return Location.builder()
                .locationId(locationDto.getLocationId())
                .placeId(locationDto.getPlaceId())
                .locationName(locationDto.getLocationName())
                .description(locationDto.getDescription())
                .latitude(locationDto.getLatitude())
                .longitude(locationDto.getLongitude())
                .googleRating(locationDto.getGoogleRating())
                .userRatingsTotal(locationDto.getUserRatingsTotal())
                .placeImgUrl(locationDto.getPlaceImgUrl())
                .formattedAddress(locationDto.getFormattedAddress())
                .openingHours(locationDto.getOpeningHours())
                .website(locationDto.getWebsite())
                .phoneNumber(locationDto.getPhoneNumber())
                .region(region) // region을 외래키로 설정
                .tags(tags) // tags를 set으로 설정
                .build();
    }
}
