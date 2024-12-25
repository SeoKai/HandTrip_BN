//package TeamGoat.TripSupporter.Service.Planner;
//
//import TeamGoat.TripSupporter.Domain.Dto.Planner.DailyPlanDto;
//import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
//import TeamGoat.TripSupporter.Domain.Dto.Planner.ToDoDto;
//import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
//import TeamGoat.TripSupporter.Domain.Entity.Planner.Planner;
//import TeamGoat.TripSupporter.Mapper.Planner.PlannerMapper;
//import TeamGoat.TripSupporter.Repository.Location.RegionRepository;
//import TeamGoat.TripSupporter.Repository.Planner.PlannerRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class PlannerServiceTest {
//
//    @Mock
//    private PlannerRepository plannerRepository;
//
//    @Mock
//    private RegionRepository regionRepository;
//
//    @Mock
//    private PlannerMapper plannerMapper;
//
//    @InjectMocks
//    private PlannerService plannerService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @DisplayName("플랜 수정 성공 테스트")
//    @Test
//    void updatePlanner_success() {
//        // Arrange
//        Long plannerId = 1L;
//        String regionName = "Tokyo";
//
//        PlannerDto plannerDto = PlannerDto.builder()
//                .plannerTitle("Updated Title")
//                .plannerStartDate(LocalDate.of(2023, 12, 1))
//                .plannerEndDate(LocalDate.of(2023, 12, 10))
//                .regionName(regionName)
//                .dailyPlans(Collections.singletonList(
//                        DailyPlanDto.builder()
//                                .planDate(LocalDate.of(2023, 12, 2))
//                                .toDos(Collections.singletonList(
//                                        ToDoDto.builder()
//                                                .locationId(1L)
//                                                .locationName("Tokyo Tower")
//                                                .formattedAddress("Tokyo, Japan")
//                                                .build()
//                                ))
//                                .build()
//                ))
//                .build();
//
//        Planner existingPlanner = mock(Planner.class);
//        Region region = mock(Region.class);
//
//        Planner updatedPlanner = mock(Planner.class);
//
//        when(plannerRepository.findById(plannerId)).thenReturn(Optional.of(existingPlanner));
//        when(regionRepository.findByRegionName(regionName)).thenReturn(Optional.of(region));
//        when(plannerMapper.toEntity(plannerDto, region)).thenReturn(updatedPlanner);
//
//        // Act
//        plannerService.updatePlanner(plannerId, plannerDto);
//
//        // Assert
//        verify(plannerRepository).findById(plannerId);
//        verify(regionRepository).findByRegionName(regionName);
//        verify(plannerMapper).toEntity(plannerDto, region);
//        verify(existingPlanner).updateWith(updatedPlanner);
//    }
//
//    @DisplayName("플랜을 찾지 못했을때")
//    @Test
//    void updatePlanner_notFoundPlanner() {
//        // Arrange
//        Long plannerId = 1L;
//        PlannerDto plannerDto = PlannerDto.builder()
//                .plannerTitle("Updated Title")
//                .plannerStartDate(LocalDate.of(2023, 12, 1))
//                .plannerEndDate(LocalDate.of(2023, 12, 10))
//                .regionName("Tokyo")
//                .build();
//
//        when(plannerRepository.findById(plannerId)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> plannerService.updatePlanner(plannerId, plannerDto)
//        );
//
//        assertEquals("플래너를 찾을 수 없습니다. ID: 1", exception.getMessage());
//        verify(plannerRepository).findById(plannerId);
//        verifyNoInteractions(regionRepository, plannerMapper);
//    }
//
//    @DisplayName("지역을 찾을 수 없을 때")
//    @Test
//    void updatePlanner_notFoundRegion() {
//        // Arrange
//        Long plannerId = 1L;
//        String regionName = "UnknownRegion";
//
//        PlannerDto plannerDto = PlannerDto.builder()
//                .plannerTitle("Updated Title")
//                .plannerStartDate(LocalDate.of(2023, 12, 1))
//                .plannerEndDate(LocalDate.of(2023, 12, 10))
//                .regionName(regionName)
//                .build();
//
//        Planner existingPlanner = mock(Planner.class);
//
//        when(plannerRepository.findById(plannerId)).thenReturn(Optional.of(existingPlanner));
//        when(regionRepository.findByRegionName(regionName)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> plannerService.updatePlanner(plannerId, plannerDto)
//        );
//
//        assertEquals("해당 지역이 존재하지 않습니다. 이름: UnknownRegion", exception.getMessage());
//        verify(plannerRepository).findById(plannerId);
//        verify(regionRepository).findByRegionName(regionName);
//        verifyNoInteractions(plannerMapper);
//    }
//}
