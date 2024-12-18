package TeamGoat.TripSupporter.Repository.Planner;

import TeamGoat.TripSupporter.Domain.Entity.Planner.DailyPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyPlanRepository extends JpaRepository<DailyPlan, Long> {
}
