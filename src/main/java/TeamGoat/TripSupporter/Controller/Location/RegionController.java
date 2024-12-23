package TeamGoat.TripSupporter.Controller.Location;

import TeamGoat.TripSupporter.Domain.Dto.Location.RegionDto;
import TeamGoat.TripSupporter.Service.Location.RegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/region")
@Slf4j
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @GetMapping("/getAll")
    public List<RegionDto> getAllRegions(){
        log.info("get /region/getAll called");
        return regionService.getAllRegions();
    }
}
