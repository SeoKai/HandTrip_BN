package TeamGoat.TripSupporter.Controller;

import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import TeamGoat.TripSupporter.Service.PlannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planner")
@RequiredArgsConstructor
@Slf4j
public class PlannerController {

    private final PlannerService plannerService;

    // 플래너 저장
    @PostMapping("/save")
    public ResponseEntity<Long> savePlanner(@RequestBody PlannerDto plannerDto) {
        Long plannerId = plannerService.savePlanner(plannerDto);
        return ResponseEntity.ok(plannerId);
    }

    // 아이디 기반 플래너 목록 조회
    @GetMapping("/{id}")
    public ResponseEntity<PlannerDto> getPlannerDetails(@PathVariable("id") Long id) {
        System.out.println("전달받은 id = " + id);
        PlannerDto planner = plannerService.getPlannerDetails(id);

        if (planner != null)
            return ResponseEntity.ok(planner);
        else
            return ResponseEntity.notFound().build();
    }

}
