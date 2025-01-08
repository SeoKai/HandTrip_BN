package TeamGoat.TripSupporter.Controller.Location;


import TeamGoat.TripSupporter.Controller.Location.Util.LocationControllerValidator;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationSplitByTagDto;
import TeamGoat.TripSupporter.Service.Location.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/locations")
@Slf4j
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private static final String DEFAULT_SORT_VALUE = "googleRating";
    private static final String DEFAULT_SORT_DIRECTION = "desc";

    @GetMapping("/getLocation")
    public LocationDto getLocation(@RequestParam("locationId") Long locationId) {
        log.info("GET /getLocation with locationId: {}", locationId);
        LocationControllerValidator.validateLocationId(locationId);
        return locationService.getLocation(locationId);
    }

    /**
     * 지역 ID, Location name의 검색어, 태그들과 , 페이지 정보를 받아 해당하는 Location들을 페이징처리하여 반환함
     * @param regionId Location의 지역 Id
     * @param keyword 검색어
     * @param tagNames 태그문자열
     * @param page 현재 페이지
     * @param pageSize 한 페이지에 몇개의 Location을 가져올지
     * @param sortValue 정렬기준
     * @param sortDirection 정렬방향
     * @return
     */
    @GetMapping("/searchLocation")
    public Page<LocationDto> getLocationByTagNames(
            @RequestParam(name = "regionId", required = false) Long regionId,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "tagNames", defaultValue = "") String tagNames,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortValue", defaultValue = DEFAULT_SORT_VALUE) String sortValue,
            @RequestParam(name = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection
    ){
        log.info("GET /searchLocation - regionId: {}, keyword: {},tagNames: {}, page: {},pageSize: {}, sortValue: {}, sortDirection: {}",
                regionId, keyword, tagNames, page,pageSize, sortValue, sortDirection);

        // tagNames를 쉼표로 분리하여 Set으로 변환
        Set<String> tagNameSet = new HashSet<>(Arrays.stream(tagNames.split(","))
                .filter(tag -> !tag.isEmpty())
                .collect(Collectors.toList()));

        // Set으로 변환된 tagNames 유효성 검사
        LocationControllerValidator.validateTagNames(tagNameSet);
        log.info("tagNameSet : " + tagNameSet);
        // 유효성 검사
        if(regionId != null){
            LocationControllerValidator.validateRegion(regionId);
        }
        LocationControllerValidator.validatePageRequest(page,sortValue,sortDirection);

        return locationService.searchLocations(regionId, keyword, tagNameSet, page, pageSize , sortValue, sortDirection);
    }

    @GetMapping("/{locationId}")
    public LocationDto getLocationWithId(@PathVariable(name = "locationId") Long locationId){
        //locationId 유효성 검사
        LocationControllerValidator.validateLocationId(locationId);

        return locationService.getLocationById(locationId);
    }

    /**
     * 특정 위도(latitude), 경도(longitude)로부터 지정된 거리 내의 장소를 정렬하여 조회
     * @param latitude 중심 위도
     * @param longitude 중심 경도
     * @param distance 반경 거리    km단위
     * @param sortValue 정렬 기준
     * @param sortDirection 정렬 방향
     * @return 반경 내 장소 리스트 (LocationResponseDto)
     */
    @GetMapping("/getNearby")
    public LocationSplitByTagDto getLocationWithinDistance(
            @RequestParam(name = "latitude") Double latitude,
            @RequestParam(name = "longitude") Double longitude,
            @RequestParam(name = "distance") Double distance,
            @RequestParam(name = "sortValue", defaultValue = DEFAULT_SORT_VALUE) String sortValue,  // 정렬 기준
            @RequestParam(name = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection, // 정렬 방향
            @RequestParam(name = "targetTagName", defaultValue = "음식") String targetTagName
    ){
        log.info("GET /getNearby - latitude: {}, longitude: {}, distance: {}, sortValue: {}, sortDirection: {}, targetTagName: {}",
                latitude, longitude, distance, sortValue, sortDirection, targetTagName);
        // 파라미터들 유효성 검사
        LocationControllerValidator.validateLatAndLon(latitude,longitude);
        LocationControllerValidator.validateDistance(distance);
        LocationControllerValidator.validateSortRequest(sortValue, sortDirection);

        return locationService.getLocationWithinDistance(latitude, longitude, distance, sortValue, sortDirection, targetTagName);
    }

}
