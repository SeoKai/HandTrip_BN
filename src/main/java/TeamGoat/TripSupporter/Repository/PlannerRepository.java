package TeamGoat.TripSupporter.Repository;

import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, Long> {
    List<Planner> findByUserUserId(Long userId); // 특정 사용자의 모든 플래너 조회
}

