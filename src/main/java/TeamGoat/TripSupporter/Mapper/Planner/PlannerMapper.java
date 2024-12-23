package TeamGoat.TripSupporter.Mapper.Planner;

import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
import TeamGoat.TripSupporter.Domain.Entity.Planner.DailyPlan;
import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * PlannerMapper는 Planner 엔티티와 PlannerDto 간의 변환을 담당합니다.
 */
@Component
@RequiredArgsConstructor
public class PlannerMapper {

    private final DailyPlanMapper dailyPlanMapper;

    /**
     * Planner 엔티티를 PlannerDto로 변환합니다.
     *
     * @param planner 변환할 Planner 엔티티
     * @return 변환된 PlannerDto
     */
    public PlannerDto toDto(Planner planner) {
        return PlannerDto.builder()
                .plannerId(planner.getPlannerId()) // 플래너 ID
                .plannerTitle(planner.getPlannerTitle()) // 플래너 제목
                .plannerStartDate(planner.getPlannerStartDate()) // 시작일
                .plannerEndDate(planner.getPlannerEndDate()) // 종료일
                .regionName(planner.getRegion().getRegionName()) // 지역 이름
                .dailyPlans(planner.getDailyPlans().stream() // 하루 일정 목록
                        .map(dailyPlanMapper::toDto)
                        .toList())
                .build();
    }

    /**
     * PlannerDto를 Planner 엔티티로 변환합니다.
     *
     * @param plannerDto 변환할 PlannerDto
     * @param region Planner와 연결할 Region 엔티티
     * @return 변환된 Planner 엔티티
     */
    public Planner toEntity(PlannerDto plannerDto, Region region) {
        Planner planner = Planner.builder()
                .plannerTitle(plannerDto.getPlannerTitle()) // 플래너 제목
                .plannerStartDate(plannerDto.getPlannerStartDate()) // 시작일
                .plannerEndDate(plannerDto.getPlannerEndDate()) // 종료일
                .email("test@example.com") // 예시 사용자 이메일
                .region(region) // 연결된 Region 엔티티
                .build();

        // DailyPlan 설정
        plannerDto.getDailyPlans().forEach(dailyPlanDto -> {
            planner.getDailyPlans().add(dailyPlanMapper.toEntity(dailyPlanDto, planner));
        });

        return planner;
    }

}
