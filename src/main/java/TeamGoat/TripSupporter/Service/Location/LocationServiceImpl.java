package TeamGoat.TripSupporter.Service.Location;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
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

    public Page<LocationResponseDto> getLocationByTagNames(Long regionId, Set<String> tagNames, int page, String sortValue, String sortDirection) {
        //입력받은 페이징정보들로 페이징 객체 생성
        Pageable pageable = getPageable(page,sortValue,sortDirection);

        //페이징객체와 tagName리스트를 입력받아 페이징처리된 location객체를 받고 유효성 검사
        Page<Location> locations = locationRepository.findByTags_TagNameInAndRegion_RegionId(tagNames,regionId,pageable);
        LocationServiceValidator.validateLocationEntity(locations);

        //페이징처리된 location객체들을 반환용Dto로 변환하고 변환이 잘 되었는지 확인
        Page<LocationResponseDto> locationResponseDtos = locations.map(locationMapper::toResponseDto);
        LocationServiceValidator.validateLocationDto(locationResponseDtos);

        return locationResponseDtos;
    }

    public Page<LocationResponseDto> getLocationByLocationName(Long regionId, String keyword,int page,String sortValue,String sortDirection){
        log.info("Find Location By Location Name Service");
        //입력받은 페이징정보들로 페이징객체 생성
        Pageable pageable = getPageable(page,sortValue,sortDirection);

        //페이징객체와 keyword를 입력받아 페이징처리된 location객체를 받고 유효성 검사
        Page<Location> locations = locationRepository.findByLocationNameContaining(regionId,keyword,pageable);
        LocationServiceValidator.validateLocationEntity(locations);

        //페이징처리된 location객체들을 반환용Dto로 변환하고 변환이 잘 되었는지 확인
        Page<LocationResponseDto> locationResponseDtos = locations.map(locationMapper::toResponseDto);
        LocationServiceValidator.validateLocationDto(locationResponseDtos);

        return locationResponseDtos;
    }

    public Page<LocationResponseDto> getLocationbyRegion(Long regionId,int page, String sortValue, String sortDirection) {
        //입력받은 페이징정보들로 페이징객체 생성
        Pageable pageable = getPageable(page, sortValue, sortDirection);

        //페이징객체와 keyword를 입력받아 페이징처리된 location객체를 받고 유효성 검사
        Page<Location> locations = locationRepository.findByRegion_RegionId(regionId,pageable);
        LocationServiceValidator.validateLocationEntity(locations);

        //페이징처리된 location객체들을 반환용Dto로 변환하고 변환이 잘 되었는지 확인
        Page<LocationResponseDto> locationResponseDtos = locations.map(locationMapper::toResponseDto);
        LocationServiceValidator.validateLocationDto(locationResponseDtos);

        return locationResponseDtos;
    }

    public Page<LocationResponseDto> getLocationAll(int page, String sortValue, String sortDirection){
        //입력받은 페이징정보들로 페이징객체 생성
        Pageable pageable = getPageable(page, sortValue, sortDirection);

        // 페이징객체를 입력하여 페이징 처리된 location객체를 받고 유효성 검사
        Page<Location> locations = locationRepository.findAll(pageable);
        LocationServiceValidator.validateLocationEntity(locations);

        // 페이징처리된 location객체들을 반환용Dto로 변환하고 변환이 잘 되었는지 확인
        Page<LocationResponseDto> locationResponseDtos = locations.map(locationMapper::toResponseDto);
        LocationServiceValidator.validateLocationDto(locationResponseDtos);

        return locationResponseDtos;
    }

    public List<LocationResponseDto> getLocationWithinDistance(Double latitude, Double longitude, Double distance, String sortValue, String sortDirection){
        log.info("getLocationWithinDistance service");

        // sort객체에 대한 유효성 검사
        Sort sort = Sort.by(Sort.Order.by(sortValue).with(Sort.Direction.fromString(sortDirection)));
        LocationServiceValidator.validateLocationSort(sort);

        // 위도, 경도, 반경, sort객체를 받아 중심위도경도로부터 반경내의 location을 정렬하여 가져오고 유효성 검사
        List<Location> locations = locationRepository.findLocationsWithinDistance(latitude, longitude, distance,sort);
        LocationServiceValidator.validateLocationEntity(locations);

        // List<Location>을 List<LocationResponseDto>로 변환하고 유효성 검사
        List<LocationResponseDto> locationResponseDtos = locations.stream().map(locationMapper::toResponseDto).toList();
        LocationServiceValidator.validateLocationDto(locationResponseDtos);

        return locationResponseDtos;
    }


    private Pageable getPageable(int page, String sortValue, String sortDirection) {
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

        Pageable pageable = PageRequest.of(page,5,sort);
        LocationServiceValidator.validatePageable(pageable);

        return pageable;
    }
}
