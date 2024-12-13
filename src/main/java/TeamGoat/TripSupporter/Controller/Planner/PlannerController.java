package TeamGoat.TripSupporter.Controller.Planner;

import TeamGoat.TripSupporter.Repository.PlannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/planners")
public class PlannerController {

    @Autowired
    PlannerRepository plannerRepository;

    /**
     * 새로운 플래너 생성
     * @param plannerDto 플래너 정보를 담은 DTO
     * @return 생성된 플래너 정보 반환
     */

    /**
     * 특정 사용자의 모든 플래너 조회
     * @param userId 사용자의 ID
     * @return 사용자의 플래너 목록 반환
     */

    /**
     * 특정 플래너 조회
     * @param plannerId 플래너의 ID
     * @return 플래너 상세 정보 반환
     */

    /**
     * 플래너 삭제
     * @param plannerId 삭제할 플래너의 ID
     */
}
