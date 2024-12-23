package TeamGoat.TripSupporter.Service.Location;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationWithDistanceDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Exception.Location.LocationNotFoundException;
import TeamGoat.TripSupporter.Mapper.Location.LocationMapper;
import TeamGoat.TripSupporter.Repository.Location.LocationRepository;
import TeamGoat.TripSupporter.Service.Location.Util.LocationServiceValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
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


    public Page<LocationDto> searchLocations(Long regionId,String keyword ,Set<String> tagNames, int page,int pageSize, String sortValue, String sortDirection) {

        //입력받은 페이징정보들로 페이징 객체 생성
        Pageable pageable = getPageable(page,pageSize,sortValue,sortDirection);
        Page<Location> locations;

        //페이징객체와 tagName리스트를 입력받아 페이징처리된 location객체를 받고 유효성 검사
        // regionId 정보가 없으면 regionId를 무시하고 검색
        if (regionId == null) {
            if(tagNames.isEmpty()){
                // regionId 정보 없고 tagNames 정보 없음
                log.info("searchWithoutRegionAndTagNames Run");
                // 키워드가 있다면 키워드기준 검색, 키워드가 없다면 모든 Location
                locations = searchWithoutRegionAndTagNames(keyword, pageable);
            }else{
                // regionId 정보 없고 tagNames 정보 있음
                log.info("searchTagNamesWithoutRegion Run");
                // 키워드가 있다면 키워드+태그 기준 검색, 키워드가 없다면 태그기준 검색
                locations = searchTagNamesWithoutRegion(tagNames, keyword, pageable);
            }
        }else{
            if(tagNames.isEmpty()){
                //regionId 정보 있고 tagNames 정보 없음
                log.info("searchRegionWithoutTagNames Run");
                // 키워드가 있다면 키워드+지역Id 기준 검색, 키워드가 없다면 지역Id기준 검색
                locations = searchRegionWithoutTagNames(regionId, keyword, pageable);
            }else{
                //regionId 정보 있고 tagName 정보 있음
                log.info("searchTagNamesAndRegion Run");
                // 키워드가 있다면 키워드+지역Id+태그 기준 검색, 키워드가 없다면 지역Id + 태그기준 검색
                locations = searchTagNamesAndRegion(regionId, tagNames, keyword, pageable);
            }
        }
        // 검색결과값 유효성 검사
        LocationServiceValidator.validateLocationEntity(locations);

        //페이징처리된 location객체들을 반환용Dto로 변환하고 변환이 잘 되었는지 확인

        Page<LocationDto> LocationDto = locations.map(locationMapper::toLocationDto);
        LocationServiceValidator.validateLocationDto(LocationDto);
        log.info("get Location by TagNames tagNames: " + tagNames);
        return LocationDto;

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






    public List<LocationResponseDto> getLocationWithinDistance(Double latitude, Double longitude, Double distance, String sortValue, String sortDirection){
        log.info("getLocationWithinDistance service");

        // sort객체에 대한 유효성 검사
        Sort sort = Sort.by(Sort.Order.by(sortValue).with(Sort.Direction.fromString(sortDirection)));
        LocationServiceValidator.validateLocationSort(sort);

        // 위도, 경도, 반경, sort객체를 받아 중심위도경도로부터 반경내의 location을 정렬하여 가져오고 유효성 검사
        List<LocationWithDistanceDto> location = locationRepository.findLocationsWithinDistance(latitude, longitude, distance,sort);
        LocationServiceValidator.validateLocationDto(location);

        List<LocationResponseDto> responseLocationDtos =
                location.stream().map(locationMapper::locationResponseDto).collect(Collectors.toList());

        LocationServiceValidator.validateLocationDto(responseLocationDtos);
        return responseLocationDtos;
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
