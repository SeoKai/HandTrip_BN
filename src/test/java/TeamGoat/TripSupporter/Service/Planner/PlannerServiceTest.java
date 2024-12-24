package TeamGoat.TripSupporter.Service.Planner;

import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlannerServiceTest {

    @Autowired
    private PlannerService plannerService;

    @Test
    void testGetAllPlansByEmail() {
        String email = "test@example.com";
        List<PlannerDto> planners = plannerService.getAllPlansByEmail(email);

        assertFalse(planners.isEmpty());
        assertEquals(email, email);
    }
}
