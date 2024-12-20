package TeamGoat.TripSupporter.Service.Planner;

import TeamGoat.TripSupporter.Domain.Dto.Planner.DailyPlanDto;
import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
import TeamGoat.TripSupporter.Domain.Dto.Planner.ToDoDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
import TeamGoat.TripSupporter.Domain.Entity.Planner.DailyPlan;
import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import TeamGoat.TripSupporter.Domain.Entity.Planner.ToDo;
import TeamGoat.TripSupporter.Repository.Location.LocationRepository;
import TeamGoat.TripSupporter.Repository.Planner.PlannerRepository;
import TeamGoat.TripSupporter.Repository.Location.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final RegionRepository regionRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public Long savePlanner(PlannerDto plannerDto) {
        // 1. Region 조회 (regionName 기준)
        Region region = regionRepository.findByRegionName(plannerDto.getRegionName())
                .orElseThrow(() -> new IllegalArgumentException("해당 지역이 존재하지 않습니다."));

        // 2. Planner 생성 및 저장
        Planner planner = Planner.builder()
                .plannerTitle(plannerDto.getPlannerTitle())
                .plannerStartDate(plannerDto.getPlannerStartDate())
                .plannerEndDate(plannerDto.getPlannerEndDate())
                .email("test@example.com") // 예시 사용자 이메일
                .region(region)
                .build();

        // 3. DailyPlan 생성 및 연결
        for (DailyPlanDto dailyPlanDto : plannerDto.getDailyPlans()) {
            DailyPlan dailyPlan = DailyPlan.builder()
                    .planDate(dailyPlanDto.getPlanDate())
                    .planner(planner)
                    .build();

            // 4. ToDo 생성 및 연결
            for (ToDoDto toDoDto : dailyPlanDto.getToDos()) {
                Location location = locationRepository.findById(toDoDto.getLocationId())
                        .orElseThrow(() -> new IllegalArgumentException("해당 장소가 존재하지 않습니다."));

                ToDo toDo = ToDo.builder()
                        .dailyPlan(dailyPlan)
                        .location(location)
                        .build();

                dailyPlan.getToDos().add(toDo);
            }

            planner.getDailyPlans().add(dailyPlan);
        }

        // 5. Planner 저장 (Cascade로 DailyPlan 및 ToDo도 함께 저장)
        Planner savedPlanner = plannerRepository.save(planner);

        return savedPlanner.getPlannerId(); // 저장된 Planner의 ID 반환
    }

    @Transactional(readOnly = true)
    public PlannerDto getPlannerDetails(Long id) {
        Planner planner = plannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 플랜이 존재하지 않습니다."));

        return PlannerDto.builder()
                .plannerId(planner.getPlannerId())
                .plannerTitle(planner.getPlannerTitle())
                .plannerStartDate(planner.getPlannerStartDate())
                .plannerEndDate(planner.getPlannerEndDate())
                .regionName(planner.getRegion().getRegionName())
                .dailyPlans(planner.getDailyPlans().stream()
                        .map(dailyPlan -> DailyPlanDto.builder()
                                .planDate(dailyPlan.getPlanDate())
                                .toDos(dailyPlan.getToDos().stream()
                                        .map(toDo -> ToDoDto.builder()
                                                .locationId(toDo.getLocation().getLocationId())
                                                .locationName(toDo.getLocation().getLocationName())
                                                .formattedAddress(toDo.getLocation().getFormattedAddress())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

}
