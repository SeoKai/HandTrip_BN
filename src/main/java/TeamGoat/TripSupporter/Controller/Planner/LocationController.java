package TeamGoat.TripSupporter.Controller.Planner;


import TeamGoat.TripSupporter.Controller.Planner.Util.LocationControllerValidator;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;
import TeamGoat.TripSupporter.Service.Location.LocationServiceImpl;
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
public class LocationController {
    private final LocationServiceImpl locationServiceImpl;

    public LocationController(LocationServiceImpl locationServiceImpl) {
        this.locationServiceImpl = locationServiceImpl;
    }

    @GetMapping("/by-region")
    public List<LocationDto> getLocationsByRegion(@RequestParam("regionId") Long regionId) {
        log.info("GET /by-region with regionId: {}", regionId);
        return locationServiceImpl.getLocationsByRegion(regionId);
    }

    @GetMapping("/by-region1")
    public Page<LocationResponseDto> getLocationByTagNames(
            @RequestParam(name = "tagNames") String[] tagNames,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sortValue", defaultValue = "googleRating") String sortValue,
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection
    ){
        log.info("GET /by-region1 - tagNames: {}, page: {}, sortValue: {}, sortDirection: {}",
                tagNames, page, sortValue, sortDirection);
        LocationControllerValidator.validateTagNames(tagNames);
        LocationControllerValidator.validatePageRequest(page,sortValue,sortDirection);
        return locationServiceImpl.getLocationByTagNames(tagNames, page, sortValue, sortDirection);
    }

    @GetMapping("/by-region2")
    public Page<LocationResponseDto> getLocationByLocationName(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sortValue", defaultValue = "googleRating") String sortValue,
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection
    ){
        log.info("GET /by-region2 - keyword: {}, page: {}, sortValue: {}, sortDirection: {}",
                keyword, page, sortValue, sortDirection);
        //파라미터 null 체크
        LocationControllerValidator.validateKeyword(keyword);
        LocationControllerValidator.validatePageRequest(page,sortValue,sortDirection);
        return locationServiceImpl.getLocationByLocationName(keyword, page, sortValue, sortDirection);
    }

    @GetMapping("/by-region3")
    public Page<LocationResponseDto> getLocationbyRegion(
            @RequestParam(name = "regionId") Long regionId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sortValue", defaultValue = "googleRating") String sortValue,
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection
    ){
        log.info("GET /by-region3 - regionId: {}, page: {}, sortValue: {}, sortDirection: {}",
                regionId, page, sortValue, sortDirection);
        LocationControllerValidator.validateRegion(regionId);
        LocationControllerValidator.validatePageRequest(page,sortValue,sortDirection);
        return locationServiceImpl.getLocationbyRegion(regionId,page,sortValue,sortDirection);
    }

    @GetMapping("/by-region4")
    public Page<LocationResponseDto> getLocationAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sortValue", defaultValue = "googleRating") String sortValue,
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection
    ){
        log.info("GET /by-region4 - page: {}, sortValue: {}, sortDirection: {}",
                page, sortValue, sortDirection);
        LocationControllerValidator.validatePageRequest(page,sortValue,sortDirection);
        return locationServiceImpl.getLocationAll(page, sortValue, sortDirection);
    }

    @GetMapping("/by-region5")
    public List<LocationResponseDto> getLocationWithinDistance(
            @RequestParam(name = "latitude") Double latitude,
            @RequestParam(name = "longitude") Double longitude,
            @RequestParam(name = "distance") Double distance,
            @RequestParam(name = "sortValue", defaultValue = "googleRating") String sortValue,  // 정렬 기준
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection // 정렬 방향
    ){
        log.info("GET /by-region5 - latitude: {}, longitude: {}, distance: {}, sortValue: {}, sortDirection: {}",
                latitude, longitude, distance, sortValue, sortDirection);
        LocationControllerValidator.validateLatAndLon(latitude,longitude);
        LocationControllerValidator.validateDistance(distance);
        LocationControllerValidator.validateSortRequest(sortValue, sortDirection);
        Sort sort = Sort.by(Sort.Order.by(sortValue).with(Sort.Direction.fromString(sortDirection)));

        return locationServiceImpl.getLocationWithinDistance(latitude, longitude, distance, sort);
    }
}
