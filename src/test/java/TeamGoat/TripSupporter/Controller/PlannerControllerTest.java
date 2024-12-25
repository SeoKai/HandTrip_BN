//package TeamGoat.TripSupporter.Controller;
//
//import TeamGoat.TripSupporter.Controller.Planner.PlannerController;
//import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
//import TeamGoat.TripSupporter.Domain.Dto.Planner.DailyPlanDto;
//import TeamGoat.TripSupporter.Domain.Dto.Planner.ToDoDto;
//import TeamGoat.TripSupporter.Service.Planner.PlannerService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class PlannerControllerTest {
//
//    @InjectMocks
//    private PlannerController plannerController;
//
//    @Mock
//    private PlannerService plannerService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testSavePlanner() {
//        // Arrange
//        PlannerDto mockPlanner = createMockPlannerDto();
//        when(plannerService.savePlanner(mockPlanner)).thenReturn(1L);
//
//        // Act
//        ResponseEntity<Long> response = plannerController.savePlanner(mockPlanner);
//
//        // Assert
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(1L, response.getBody());
//        verify(plannerService, times(1)).savePlanner(mockPlanner);
//    }
//
//    @Test
//    public void testGetPlannerDetails() {
//        // Arrange
//        Long plannerId = 1L;
//        PlannerDto mockPlanner = createMockPlannerDto();
//        when(plannerService.getPlannerDetails(plannerId)).thenReturn(mockPlanner);
//
//        // Act
//        ResponseEntity<PlannerDto> response = plannerController.getPlannerDetails(plannerId);
//
//        // Assert
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(mockPlanner, response.getBody());
//        verify(plannerService, times(1)).getPlannerDetails(plannerId);
//    }
//
//    @Test
//    public void testUpdatePlanner() {
//        // Arrange
//        Long plannerId = 1L;
//        PlannerDto mockPlanner = createMockPlannerDto();
//        doNothing().when(plannerService).updatePlanner(plannerId, mockPlanner);
//
//        // Act
//        ResponseEntity<Void> response = plannerController.updatePlanner(plannerId, mockPlanner);
//
//        // Assert
//        assertEquals(200, response.getStatusCodeValue());
//        verify(plannerService, times(1)).updatePlanner(plannerId, mockPlanner);
//    }
//
//    private PlannerDto createMockPlannerDto() {
//        PlannerDto plannerDto = new PlannerDto();
//        plannerDto.setPlannerTitle("Test Planner");
//        plannerDto.setPlannerStartDate(LocalDate.of(2024, 12, 23));
//        plannerDto.setPlannerEndDate(LocalDate.of(2024, 12, 25));
//        plannerDto.setRegionName("Test Region");
//
//        List<DailyPlanDto> dailyPlans = new ArrayList<>();
//        DailyPlanDto dailyPlan = new DailyPlanDto();
//        dailyPlan.setPlanDate(LocalDate.of(2024, 12, 23));
//
//        List<ToDoDto> toDos = new ArrayList<>();
//        ToDoDto toDo = new ToDoDto();
//        toDo.setLocationId(1L);
//        toDo.setLocationName("Test Location");
//        toDo.setFormattedAddress("123 Test Street");
//        toDo.setLatitude(35.6895);
//        toDo.setLongitude(139.6917);
//        toDos.add(toDo);
//
//        dailyPlan.setToDos(toDos);
//        dailyPlans.add(dailyPlan);
//        plannerDto.setDailyPlans(dailyPlans);
//
//        return plannerDto;
//    }
//}
