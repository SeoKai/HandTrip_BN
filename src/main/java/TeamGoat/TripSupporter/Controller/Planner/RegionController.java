package TeamGoat.TripSupporter.Controller.Planner;

import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
import TeamGoat.TripSupporter.Domain.Dto.Planner.TodoDto;
import TeamGoat.TripSupporter.Service.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/planner")
@RequiredArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;

    // 플래너 생성 (도시, 출발일, 도착일 저장)
    @PostMapping
    public PlannerDto createPlanner(@RequestBody PlannerDto plannerDto) {
        return plannerService.createPlanner(plannerDto);
    }

    // 특정 플래너에 일정 추가
    @PostMapping("/{id}/todo")
    public TodoDto addTodo(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        return plannerService.addTodoToPlanner(id, todoDto);
    }

    // 특정 플래너의 일정 조회
    @GetMapping("/{id}")
    public PlannerDto getPlanner(@PathVariable Long id) {
        return plannerService.getPlannerById(id);
    }
}
