package TeamGoat.TripSupporter.Service.Location;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationSplitByTagDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationWithDistanceDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Exception.Location.LocationNotFoundException;
import TeamGoat.TripSupporter.Mapper.Location.LocationMapper;
import TeamGoat.TripSupporter.Repository.Location.LocationRepository;
import TeamGoat.TripSupporter.Service.Location.Util.LocationServiceValidator;
import TeamGoat.TripSupporter.Service.Location.Util.PhotoUrlGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    // 생성자 주입
    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    // regionId를 기반으로 장소 조회
    public LocationDto getLocation(Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationNotFoundException("해당 위치를 찾을 수 없습니다."));

        // Location -> DTO 변환
        LocationDto locationDto = locationMapper.toLocationDto(location);

        // placeImgUrl 동적 생성
        String dynamicPhotoUrl = PhotoUrlGenerator.generatePhotoUrl(location.getPlaceImgUrl());
        locationDto.setPlaceImgUrl(dynamicPhotoUrl);

        return locationDto;
    }


    public Page<LocationDto> searchLocations(Long regionId, String keyword, Set<String> tagNames, int page, int pageSize, String sortValue, String sortDirection) {
        Pageable pageable = getPageable(page, pageSize, sortValue, sortDirection);
        Page<Location> locations;

        // 검색 조건에 따라 Location 가져오기
        if (regionId == null) {
            if (tagNames.isEmpty()) {
                log.info("searchWithoutRegionAndTagNames Run");
                locations = searchWithoutRegionAndTagNames(keyword, pageable);
            } else {
                log.info("searchTagNamesWithoutRegion Run");
                locations = searchTagNamesWithoutRegion(tagNames, keyword, pageable);
            }
        } else {
            if (tagNames.isEmpty()) {
                log.info("searchRegionWithoutTagNames Run");
                locations = searchRegionWithoutTagNames(regionId, keyword, pageable);
            } else {
                log.info("searchTagNamesAndRegion Run");
                locations = searchTagNamesAndRegion(regionId, tagNames, keyword, pageable);
            }
        }

        // 검색 결과값 유효성 검사
        LocationServiceValidator.validateLocationEntity(locations);

        // DTO 변환 및 placeImgUrl 동적 생성
        Page<LocationDto> locationDtos = locations.map(location -> {
            LocationDto locationDto = locationMapper.toLocationDto(location);
            String dynamicPhotoUrl = PhotoUrlGenerator.generatePhotoUrl(location.getPlaceImgUrl());
            locationDto.setPlaceImgUrl(dynamicPhotoUrl);
            return locationDto;
        });

        LocationServiceValidator.validateLocationDto(locationDtos);
        log.info("get Location by TagNames tagNames: " + tagNames);
        return locationDtos;
    }


    // region과 tagnames 둘다 없을때 keyword유무에 따라 검색
    private Page<Location> searchWithoutRegionAndTagNames(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return locationRepository.findAll(pageable);  // keyword가 없으면 모든 Location
        }
        return locationRepository.findByLocationNameContaining(keyword, pageable);  // keyword로 검색
    }

    private Page<Location> searchTagNamesWithoutRegion(Set<String>tagNames, String keyword, Pageable pageable){
        if(keyword == null || keyword.trim().isEmpty()){
            return locationRepository.findByTags(tagNames,tagNames.size(),pageable);
        }
        return locationRepository.findByTagsAndKeyword(tagNames,tagNames.size(),keyword, pageable);
    }

    private Page<Location> searchRegionWithoutTagNames(Long regionId, String keyword, Pageable pageable){
        if(keyword == null || keyword.trim().isEmpty()){
            return locationRepository.findByRegion_RegionId(regionId,pageable);
        }
        return locationRepository.findByRegion_RegionIdAndLocationNameContaining(regionId, keyword, pageable);
    }

    private Page<Location> searchTagNamesAndRegion(Long regionId, Set<String> tagNames, String keyword, Pageable pageable){
        if(keyword == null || keyword.trim().isEmpty()){
            return locationRepository.findByTagsAndRegion(tagNames, tagNames.size(), regionId, pageable);
        }
        return locationRepository.findByTagsAndRegionAndKeyword(tagNames, tagNames.size(), regionId, keyword, pageable);
    }


    public LocationSplitByTagDto getLocationWithinDistance(Double latitude, Double longitude, Double distance, String sortValue, String sortDirection,String targetTagName){
        log.info("getLocationWithinDistance service");

        // sort객체에 대한 유효성 검사
        Sort sort = Sort.by(Sort.Order.by(sortValue).with(Sort.Direction.fromString(sortDirection)));
        LocationServiceValidator.validateLocationSort(sort);

        // 위도, 경도, 반경, sort객체를 받아 중심위도경도로부터 반경내의 location을 정렬하여 가져오고 유효성 검사
        List<LocationWithDistanceDto> location = locationRepository.findLocationsWithinDistance(latitude, longitude, distance,sort);
        LocationServiceValidator.validateLocationDto(location);

        // 태그별로 LocationResponseDto를 담을 빈 List
        List<LocationResponseDto> locationWithTag = new ArrayList<>();
        List<LocationResponseDto> locationWithoutTag = new ArrayList<>();

        // location에서 targetTagName에 따라 분리
        for (LocationWithDistanceDto locationDto : location) {
            LocationResponseDto locationResponseDto = locationMapper.toResponseDtoWithDistance(locationDto);

            boolean hasTargetTag = locationDto.getLocation().getTags().stream()
                    .anyMatch(tag -> tag.getTagName().equalsIgnoreCase(targetTagName));

            if (hasTargetTag) {
                locationWithTag.add(locationResponseDto);
            } else {
                locationWithoutTag.add(locationResponseDto);
            }
        }

        // LocationSplitByTagDto로 묶어 반환
        return LocationSplitByTagDto.builder()
                .locationResponseDtoIncludeTag(locationWithTag)
                .locationResponseDtoExcludeTag(locationWithoutTag)
                .build();

    }


    private Pageable getPageable(int page,int pageSize, String sortValue, String sortDirection) {
        // 정렬기준, 정렬 방향을 설정하지 않았을 경우 default 정렬 기준 : 구글 평점 순
        if (sortValue == null || sortValue.isEmpty()) {
            sortValue = "googleRating";  // 기본 정렬 기준: googleRating
        }
        if (sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "desc";  // 기본 정렬 방향: 내림차순
        }

        // 정렬 기준을 sortValue로 설정
        Sort sort = Sort.by(sortValue);
        // 정렬 방향 확인
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        log.info("현재 Page : " + page + ",정렬 기준 : " + sortValue + ",정렬 방향 : " +sortDirection);

        Pageable pageable = PageRequest.of(page,pageSize,sort);
        LocationServiceValidator.validatePageable(pageable);

        return pageable;
    }


    public LocationDto getLocationById(Long locationId) {
        // Id로 Location가져오기
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationNotFoundException("여행지 정보를 찾을 수 없습니다."));
        // 유효성 검사
        LocationServiceValidator.validateLocationEntity(location);
        // Locaation -> LocaationDto
        LocationDto locationDto = locationMapper.toLocationDto(location);
        // 유효성 검사
        LocationServiceValidator.validateLocationDto(locationDto);
        return locationDto;
    }
}
