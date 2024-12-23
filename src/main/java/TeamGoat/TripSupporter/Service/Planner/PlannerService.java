package TeamGoat.TripSupporter.Service.Planner;

import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import TeamGoat.TripSupporter.Mapper.Planner.PlannerMapper;
import TeamGoat.TripSupporter.Repository.Location.LocationRepository;
import TeamGoat.TripSupporter.Repository.Location.RegionRepository;
import TeamGoat.TripSupporter.Repository.Planner.PlannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final RegionRepository regionRepository;
    private final LocationRepository locationRepository;
    private final PlannerMapper plannerMapper;

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

    @Transactional(readOnly = true)
    public PlannerDto getPlannerDetails(Long id) {
        Planner planner = plannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 플랜이 존재하지 않습니다."));

        return plannerMapper.toDto(planner);
    }
}
