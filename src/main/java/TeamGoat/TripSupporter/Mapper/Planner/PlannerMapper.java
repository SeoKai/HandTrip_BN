package TeamGoat.TripSupporter.Mapper.Planner;

import TeamGoat.TripSupporter.Domain.Dto.Planner.DailyPlanDto;
import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
import TeamGoat.TripSupporter.Domain.Dto.Planner.ToDoDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import TeamGoat.TripSupporter.Service.Location.Util.PhotoUrlGenerator;
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
        PlannerDto dto = PlannerDto.builder()
                .plannerId(planner.getPlannerId())
                .plannerTitle(planner.getPlannerTitle())
                .plannerStartDate(planner.getPlannerStartDate()) // 시작일
                .plannerEndDate(planner.getPlannerEndDate()) // 종료일
                .regionId(planner.getRegion().getRegionId()) // 지역 ID
                .regionName(planner.getRegion().getRegionName()) // 지역 이
                .userEmail(planner.getEmail()) // 이메일
                .dailyPlans(planner.getDailyPlans().stream()
                        .map(dailyPlan -> {
                            DailyPlanDto dailyPlanDto = new DailyPlanDto();
                            dailyPlanDto.setPlanDate(dailyPlan.getPlanDate());
                            dailyPlanDto.setToDos(dailyPlan.getToDos().stream()
                                    .map(toDo -> {
                                        ToDoDto toDoDto = new ToDoDto();
                                        // `ToDo`의 `Location` 데이터를 매핑
                                        toDoDto.setLocationId(toDo.getLocation().getLocationId());
                                        toDoDto.setLocationName(toDo.getLocation().getLocationName());
                                        toDoDto.setFormattedAddress(toDo.getLocation().getFormattedAddress());
                                        toDoDto.setLatitude(toDo.getLocation().getLatitude());
                                        toDoDto.setLongitude(toDo.getLocation().getLongitude());
                                        // 이미지 URL 동적 생성
                                        String dynamicPhotoUrl = PhotoUrlGenerator.generatePhotoUrl(toDo.getLocation().getPlaceImgUrl());
                                        toDoDto.setPlaceImgUrl(dynamicPhotoUrl);
                                        return toDoDto;
                                    })
                                    .toList());
                            return dailyPlanDto;
                        })
                        .toList())
                .build();

        return dto;
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
                .email(plannerDto.getUserEmail()) // 예시 사용자 이메일
                .region(region) // 연결된 Region 엔티티
                .build();

        // DailyPlan 설정
        plannerDto.getDailyPlans().forEach(dailyPlanDto -> {
            planner.getDailyPlans().add(dailyPlanMapper.toEntity(dailyPlanDto, planner));
        });

        return planner;
    }

}
