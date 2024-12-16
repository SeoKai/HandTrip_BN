package TeamGoat.TripSupporter.Service;

import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
import TeamGoat.TripSupporter.Domain.Dto.Planner.TodoDto;
import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import TeamGoat.TripSupporter.Repository.PlannerRepository;
import TeamGoat.TripSupporter.Repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final RegionRepository regionRepository;

    // 플래너 생성
    public PlannerDto createPlanner(PlannerDto plannerDto) {



        return toDto(savedPlanner);
    }


    // 엔티티 -> DTO 변환
    private PlannerDto toDto(Planner planner) {
        return PlannerDto.builder()
                .plannerId(planner.getPlannerId())
                .plannerTitle(planner.getPlannerTitle())
                .plannerStartDate(planner.getPlannerStartDate())
                .plannerEndDate(planner.getPlannerEndDate())
                .regionId(planner.getRegion().getRegionId())
                .regionName(planner.getRegion().getRegionName())
                .todos(planner.getTodos().stream()
                        .map(todo -> TodoDto.builder()
                                .todoId(todo.getTodoId())
                                .description(todo.getDescription())
                                .startTime(todo.getStartTime())
                                .endTime(todo.getEndTime())
                                .plannerId(planner.getPlannerId())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
