package TeamGoat.TripSupporter.Service.Location;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Mapper.Location.LocationMapper;
import TeamGoat.TripSupporter.Repository.Location.LocationRepository;
import TeamGoat.TripSupporter.Service.Location.Util.LocationServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationServiceImpl {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;


    // regionId를 기반으로 장소 조회
    public List<LocationDto> getLocationsByRegion(Long regionId) {
        List<Location> locations = locationRepository.findByRegionRegionId(regionId); // Repository 메서드 호출
        return locations.stream()
                .map(locationMapper::toLocationDto) // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }

    /**
     * 태그 이름(tagNames) 목록에 해당하는 장소를 페이징 처리하여 조회
     * @param tagNames 태그 이름 배열
     * @param page 페이지 번호
     * @param sortValue 정렬 기준
     * @param sortDirection 정렬 방향
     * @return 태그 조건에 맞는 장소 페이지 (LocationResponseDto)
     */
    public Page<LocationResponseDto> getLocationByTagNames(String[] tagNames, int page, String sortValue, String sortDirection) {
        //입력받은 페이징정보들로 페이징 객체 생성
        Pageable pageable = getPageable(page,sortValue,sortDirection);

        //페이징객체와 tagName리스트를 입력받아 페이징처리된 location객체를 받고 유효성 검사
        Page<Location> locations = locationRepository.findByTags_TagNameIn(tagNames,pageable);
        LocationServiceValidator.validateLocationEntity(locations);

        //페이징처리된 location객체들을 반환용Dto로 변환하고 변환이 잘 되었는지 확인
        Page<LocationResponseDto> locationResponseDtos = locations.map(locationMapper::toResponseDto);
        LocationServiceValidator.validateLocationDto(locationResponseDtos);

        return locationResponseDtos;
    }

    /**
     * 장소 이름(keyword)을 포함하는 장소를 페이징 처리하여 조회
     * @param keyword 검색 키워드
     * @param page 페이지 번호
     * @param sortValue 정렬 기준
     * @param sortDirection 정렬 방향
     * @return 키워드 조건에 맞는 장소 페이지 (LocationResponseDto)
     */
    public Page<LocationResponseDto> getLocationByLocationName(String keyword,int page,String sortValue,String sortDirection){
        log.info("Find Location By Location Name Service");
        //입력받은 페이징정보들로 페이징객체 생성
        Pageable pageable = getPageable(page,sortValue,sortDirection);

        //페이징객체와 keyword를 입력받아 페이징처리된 location객체를 받고 유효성 검사
        Page<Location> locations = locationRepository.findByLocationNameContaining(keyword,pageable);
        LocationServiceValidator.validateLocationEntity(locations);

        //페이징처리된 location객체들을 반환용Dto로 변환하고 변환이 잘 되었는지 확인
        Page<LocationResponseDto> locationResponseDtos = locations.map(locationMapper::toResponseDto);
        LocationServiceValidator.validateLocationDto(locationResponseDtos);

        return locationResponseDtos;
    }

    /**
     * 특정 지역 ID(regionId)에 해당하는 장소를 페이징 처리하여 조회
     * @param regionId 지역 ID
     * @param page 페이지 번호
     * @param sortValue 정렬 기준
     * @param sortDirection 정렬 방향
     * @return 지역 조건에 맞는 장소 페이지 (LocationResponseDto)
     */
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

    /**
     * 모든 장소를 페이징 처리하여 조회
     * @param page 페이지 번호
     * @param sortValue 정렬 기준
     * @param sortDirection 정렬 방향
     * @return 모든 장소 페이지 (LocationResponseDto)
     */
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

    /**
     * 특정 위도(latitude), 경도(longitude)로부터 지정된 거리 내의 장소를 정렬하여 조회
     * @param latitude 중심 위도
     * @param longitude 중심 경도
     * @param distance 반경 거리
     * @param sort 정렬 객체
     * @return 반경 내 장소 리스트 (LocationResponseDto)
     */
    public List<LocationResponseDto> getLocationWithinDistance(Double latitude, Double longitude, Double distance, Sort sort){
        log.info("getLocationWithinDistance service Param = latitude : "+latitude+", longitude : "+longitude+", distance : "+distance+", sort : "+sort);
        // sort객체에 대한 유효성 검사
        LocationServiceValidator.validateLocationSort(sort);

        // 위도, 경도, 반경, sort객체를 받아 중심위도경도로부터 반경내의 location을 정렬하여 가져오고 유효성 검사
        List<Location> locations = locationRepository.findLocationsWithinDistance(latitude, longitude, distance,sort);
        LocationServiceValidator.validateLocationEntity(locations);

        // List<Location>을 List<LocationResponseDto>로 변환하고 유효성 검사
        List<LocationResponseDto> locationResponseDtos = locations.stream().map(locationMapper::toResponseDto).toList();
        LocationServiceValidator.validateLocationDto(locationResponseDtos);

        return locationResponseDtos;
    }


    /**
     * 페이지 객체(Pageable)를 생성
     * @param page 페이지 번호
     * @param sortValue 정렬 기준
     * @param sortDirection 정렬 방향
     * @return 생성된 Pageable 객체
     */
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

        return PageRequest.of(page, 15, sort);
    }
}
