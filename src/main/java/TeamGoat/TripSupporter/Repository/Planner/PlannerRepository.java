package TeamGoat.TripSupporter.Repository.Planner;

import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
    List<Planner> findByEmail(String email);
}
