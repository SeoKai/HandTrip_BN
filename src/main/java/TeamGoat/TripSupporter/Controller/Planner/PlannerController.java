package TeamGoat.TripSupporter.Controller.Planner;

import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
import TeamGoat.TripSupporter.Service.Planner.PlannerService;
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

    // 플랜 아이디 기반 플래너 목록 조회
    @GetMapping("/{id}")
    public ResponseEntity<PlannerDto> getPlannerDetails(@PathVariable("id") Long id) {
        System.out.println("전달받은 id = " + id);
        PlannerDto planner = plannerService.getPlannerDetails(id);

        if (planner != null)
            return ResponseEntity.ok(planner);
        else
            return ResponseEntity.notFound().build();
    }

    // 사용자 이메일 기반 조회
    @GetMapping("/user/plans")
    public ResponseEntity<List<PlannerDto>> getAllPlansByEmail() {
        String email = "test@example.com";
        List<PlannerDto> plannerDtos = plannerService.getAllPlansByEmail(email);
        log.info("사용자 email" + email + "DTO" + plannerDtos);
        return ResponseEntity.ok(plannerDtos);
    }



//    // 플래너 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<Void> updatePlanner(@PathVariable Long id, @RequestBody PlannerDto plannerDto) {
//        plannerService.updatePlanner(id, plannerDto);
//        return ResponseEntity.ok().build();
//    }

}
