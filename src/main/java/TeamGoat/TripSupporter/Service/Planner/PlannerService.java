package TeamGoat.TripSupporter.Service.Planner;

import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import TeamGoat.TripSupporter.Mapper.Planner.PlannerMapper;
import TeamGoat.TripSupporter.Repository.Location.RegionRepository;
import TeamGoat.TripSupporter.Repository.Planner.PlannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final RegionRepository regionRepository;
    private final PlannerMapper plannerMapper;


    // 플랜 저장 서비스
    @Transactional
    public Long savePlanner(PlannerDto plannerDto) {
        // 1. Region 조회
        Region region = regionRepository.findByRegionName(plannerDto.getRegionName())
                .orElseThrow(() -> new IllegalArgumentException("해당 지역이 존재하지 않습니다."));

        // 2. PlannerDto -> Planner 변환 및 저장
        Planner planner = plannerMapper.toEntity(plannerDto, region);
        Planner savedPlanner = plannerRepository.save(planner);

        return savedPlanner.getPlannerId();
    }

    // 플랜 Id 기반으로 플랜 조회
    @Transactional(readOnly = true)
    public PlannerDto getPlannerDetails(Long id) {
        Planner planner = plannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 플랜이 존재하지 않습니다."));

        return plannerMapper.toDto(planner);
    }

    // 이메일 기반으로 조회
    @Transactional(readOnly = true)
    public List<PlannerDto> getAllPlansByEmail(String email) {
        List<Planner> planners = plannerRepository.findByEmail(email);

        if (planners.isEmpty()) {
            throw new IllegalArgumentException("해당 이메일의 플랜이 존재하지 않습니다.");
        }

        return planners.stream()
                .map(plannerMapper::toDto) // mapper를 사용해 Entity를 DTO로 변환
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePlanner(Long id, PlannerDto plannerDto) {
        Planner existingPlanner = plannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("플래너를 찾을 수 없습니다. ID: " + id));

        Region region = regionRepository.findByRegionName(plannerDto.getRegionName())
                .orElseThrow(() -> new IllegalArgumentException("해당 지역이 존재하지 않습니다. 이름: " + plannerDto.getRegionName()));

        Planner updatedPlanner = plannerMapper.toEntity(plannerDto, region);

        // 기존 Planner 업데이트
        existingPlanner.updateWith(updatedPlanner);

        System.out.println("Updated Planner: " + existingPlanner);
        existingPlanner.getDailyPlans().forEach(dailyPlan -> {
            System.out.println("Updated DailyPlan: " + dailyPlan);
            dailyPlan.getToDos().forEach(toDo -> {
                System.out.println("Updated ToDo: " + toDo);
            });
        });


        // Planner 저장 (변경사항 반영)
        plannerRepository.save(existingPlanner);
    }

    @Transactional
    public void deletePlanner(Long id) {
        Planner planner = plannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("플래너를 찾을 수 없습니다. ID: " + id));
        plannerRepository.delete(planner);
    }

}
