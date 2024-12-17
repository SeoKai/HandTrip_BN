package TeamGoat.TripSupporter.Controller;

import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
import TeamGoat.TripSupporter.Service.PlannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/planner")
@RequiredArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;

    @PostMapping("/save")
    public ResponseEntity<String> savePlanner(@RequestBody PlannerDto plannerDto) {
        Long plannerId = plannerService.savePlanner(plannerDto);
        return ResponseEntity.ok("플래너 저장 성공! ID: " + plannerId);
    }
}
