package TeamGoat.TripSupporter.Controller.Location;


import TeamGoat.TripSupporter.Controller.Location.Util.LocationControllerValidator;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;
import TeamGoat.TripSupporter.Service.Location.LocationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/searchLocation")
    public Page<LocationResponseDto> getLocationByTagNames(
            @RequestParam(name = "regionId", required = false) Long regionId,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "tagNames", defaultValue = "") String tagNames,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortValue", defaultValue = DEFAULT_SORT_VALUE) String sortValue,
            @RequestParam(name = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection
    ){
        log.info("GET /by-region1 - regionId: {}, keyword: {}, tagNames: {}, page: {}, sortValue: {}, sortDirection: {}",
                regionId, keyword, tagNames, page, sortValue, sortDirection);

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

        return locationServiceImpl.searchLocations(regionId, keyword, tagNameSet, page, pageSize , sortValue, sortDirection);
    }

    @GetMapping("/{locationId}")
    public LocationDto getLocationWithId(@PathVariable(name = "locationId") Long locationId){
        //locationId 유효성 검사
        LocationControllerValidator.validateLocationId(locationId);

        return locationServiceImpl.getLocationById(locationId);
    }

    /**
     * 특정 위도(latitude), 경도(longitude)로부터 지정된 거리 내의 장소를 정렬하여 조회
     * @param latitude 중심 위도
     * @param longitude 중심 경도
     * @param distance 반경 거리
     * @param sortValue 정렬 기준
     * @param sortDirection 정렬 방향
     * @return 반경 내 장소 리스트 (LocationResponseDto)
     */
    @GetMapping("/getNearby")
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

    /**
     * 특정 태그 및 지역에 따라 장소 목록을 필터링하는 API 엔드포인트
     *
     * @param tagName 필터링할 태그 이름
     * @return 필터링된 장소 목록
     */
    @GetMapping("/filter-by-tag")
    public ResponseEntity<List<LocationDto>> getLocationsByTag(
            @RequestParam("tagName") String tagName) {
        // 서비스에서 태그 및 지역에 해당하는 장소 목록 조회
        List<LocationDto> locations = locationServiceImpl.findLocationsByTag(tagName);
        // 결과를 클라이언트에 반환
        return ResponseEntity.ok(locations);
    }
}
