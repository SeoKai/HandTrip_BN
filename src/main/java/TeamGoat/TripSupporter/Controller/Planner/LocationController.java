package TeamGoat.TripSupporter.Controller.Planner;


import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Service.LocationServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
}
