package TeamGoat.TripSupporter.Repository.PlanRepository;

import TeamGoat.TripSupporter.Domain.Entity.Planner.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}
