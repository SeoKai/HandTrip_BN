package TeamGoat.TripSupporter.Controller.Location;


import TeamGoat.TripSupporter.Controller.Location.Util.LocationControllerValidator;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;
import TeamGoat.TripSupporter.Service.Location.LocationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@Slf4j
@RequiredArgsConstructor
public class LocationController {

    private final LocationServiceImpl locationServiceImpl;
    private static final String DEFAULT_SORT_VALUE = "googleRating";
    private static final String DEFAULT_SORT_DIRECTION = "desc";

    @GetMapping("/by-region")
    public List<LocationDto> getLocationsByRegion(@RequestParam("regionId") Long regionId) {
        log.info("GET /by-region with regionId: {}", regionId);
        return locationServiceImpl.getLocationsByRegion(regionId);
    }

    /**
     * 특정 태그 이름(tagNames)에 해당하는 장소를 페이징 처리하여 조회
     * @param tagNames 태그 이름 배열
     * @param page 페이지 번호
     * @param sortValue 정렬 기준
     * @param sortDirection 정렬 방향
     * @return 태그 조건에 맞는 장소 페이지 (LocationResponseDto)
     */
    @GetMapping("/by-region1")
    public Page<LocationResponseDto> getLocationByTagNames(
            @RequestParam(name = "regionId") Long regionId,
            @RequestParam(name = "tagNames") String[] tagNames,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sortValue", defaultValue = DEFAULT_SORT_VALUE) String sortValue,
            @RequestParam(name = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection
    ){
        log.info("GET /by-region1 - tagNames: {}, page: {}, sortValue: {}, sortDirection: {}",
                tagNames, page, sortValue, sortDirection);
        LocationControllerValidator.validateRegion(regionId);
        LocationControllerValidator.validateTagNames(tagNames);
        LocationControllerValidator.validatePageRequest(page,sortValue,sortDirection);
        return locationServiceImpl.getLocationByTagNames(regionId, tagNames, page, sortValue, sortDirection);
    }
//
//
    /**
     * 장소 이름(keyword)을 포함하는 장소를 페이징 처리하여 조회
     * @param keyword 검색 키워드
     * @param page 페이지 번호
     * @param sortValue 정렬 기준
     * @param sortDirection 정렬 방향
     * @return 키워드 조건에 맞는 장소 페이지 (LocationResponseDto)
     */
    @GetMapping("/by-region2")
    public Page<LocationResponseDto> getLocationByLocationName(
            @RequestParam(name = "regionId") Long regionId,
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sortValue", defaultValue = DEFAULT_SORT_VALUE) String sortValue,
            @RequestParam(name = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection
    ){
        log.info("GET /by-region2 - keyword: {}, page: {}, sortValue: {}, sortDirection: {}",
                keyword, page, sortValue, sortDirection);
        //파라미터 null 체크
        LocationControllerValidator.validateRegion(regionId);
        LocationControllerValidator.validateKeyword(keyword);
        LocationControllerValidator.validatePageRequest(page,sortValue,sortDirection);
        return locationServiceImpl.getLocationByLocationName(regionId, keyword, page, sortValue, sortDirection);
    }
//
//    /**
//     * 특정 지역 ID(regionId)에 해당하는 장소를 페이징 처리하여 조회
//     * @param regionId 조회할 지역 ID
//     * @param page 페이지 번호
//     * @param sortValue 정렬 기준
//     * @param sortDirection 정렬 방향
//     * @return 지역 조건에 맞는 장소 페이지 (LocationResponseDto)
//     */
//    @GetMapping("/by-region3")
//    public Page<LocationResponseDto> getLocationbyRegion(
//            @RequestParam(name = "regionId") Long regionId,
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "sortValue", defaultValue = "googleRating") String sortValue,
//            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection
//    ){
//        log.info("GET /by-region3 - regionId: {}, page: {}, sortValue: {}, sortDirection: {}",
//                regionId, page, sortValue, sortDirection);
//        LocationControllerValidator.validateRegion(regionId);
//        LocationControllerValidator.validatePageRequest(page,sortValue,sortDirection);
//        return locationServiceImpl.getLocationbyRegion(regionId,page,sortValue,sortDirection);
//    }

//    /**
//     * 모든 장소를 페이징 처리하여 조회
//     * @param page 페이지 번호
//     * @param sortValue 정렬 기준
//     * @param sortDirection 정렬 방향
//     * @return 모든 장소 페이지 (LocationResponseDto)
//     */
//    @GetMapping("/by-region4")
//    public Page<LocationResponseDto> getLocationAll(
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "sortValue", defaultValue = "googleRating") String sortValue,
//            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection
//    ){
//        log.info("GET /by-region4 - page: {}, sortValue: {}, sortDirection: {}",
//                page, sortValue, sortDirection);
//        LocationControllerValidator.validatePageRequest(page,sortValue,sortDirection);
//        return locationServiceImpl.getLocationAll(page, sortValue, sortDirection);
//    }

    /**
     * 특정 위도(latitude), 경도(longitude)로부터 지정된 거리 내의 장소를 정렬하여 조회
     * @param latitude 중심 위도
     * @param longitude 중심 경도
     * @param distance 반경 거리
     * @param sortValue 정렬 기준
     * @param sortDirection 정렬 방향
     * @return 반경 내 장소 리스트 (LocationResponseDto)
     */
    @GetMapping("/by-region5")
    public List<LocationResponseDto> getLocationWithinDistance(
            @RequestParam(name = "latitude") Double latitude,
            @RequestParam(name = "longitude") Double longitude,
            @RequestParam(name = "distance") Double distance,
            @RequestParam(name = "sortValue", defaultValue = DEFAULT_SORT_VALUE) String sortValue,  // 정렬 기준
            @RequestParam(name = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection // 정렬 방향
    ){
        log.info("GET /by-region5 - latitude: {}, longitude: {}, distance: {}, sortValue: {}, sortDirection: {}",
                latitude, longitude, distance, sortValue, sortDirection);
        LocationControllerValidator.validateLatAndLon(latitude,longitude);
        LocationControllerValidator.validateDistance(distance);
        LocationControllerValidator.validateSortRequest(sortValue, sortDirection);

        return locationServiceImpl.getLocationWithinDistance(latitude, longitude, distance, sortValue, sortDirection);
    }
}
