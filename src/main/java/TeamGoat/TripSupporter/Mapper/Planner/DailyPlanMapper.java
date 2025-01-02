package TeamGoat.TripSupporter.Mapper.Planner;

import TeamGoat.TripSupporter.Domain.Dto.Planner.DailyPlanDto;
import TeamGoat.TripSupporter.Domain.Entity.Planner.DailyPlan;
import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * DailyPlanMapper는 DailyPlan 엔티티와 DailyPlanDto 간의 변환을 담당합니다.
 */
@Component
@RequiredArgsConstructor
public class DailyPlanMapper {

    private final ToDoMapper toDoMapper;

    /**
     * DailyPlan 엔티티를 DailyPlanDto로 변환합니다.
     *
     * @param dailyPlan 변환할 DailyPlan 엔티티
     * @return 변환된 DailyPlanDto
     */
    public DailyPlanDto toDto(DailyPlan dailyPlan) {
        return DailyPlanDto.builder()
                .dailyPlanId(dailyPlan.getDailyPlanId()) // 하루 일정 ID
                .planDate(dailyPlan.getPlanDate()) // 하루 날짜
                .toDos(dailyPlan.getToDos().stream() // ToDo 목록
                        .map(toDoMapper::toDto)
                        .toList())
                .build();
    }

    /**
     * DailyPlanDto를 DailyPlan 엔티티로 변환합니다.
     *
     * @param dailyPlanDto 변환할 DailyPlanDto
     * @param planner DailyPlan과 연결할 Planner 엔티티
     * @return 변환된 DailyPlan 엔티티
     */
    public DailyPlan toEntity(DailyPlanDto dailyPlanDto, Planner planner) {
        DailyPlan dailyPlan = DailyPlan.builder()
                .planDate(dailyPlanDto.getPlanDate()) // 하루 날짜
                .planner(planner) // 연결된 Planner
                .build();

        // ToDo 설정
        dailyPlanDto.getToDos().forEach(toDoDto -> {
            dailyPlan.getToDos().add(toDoMapper.toEntity(toDoDto, dailyPlan));
        });

        return dailyPlan;
    }
}
