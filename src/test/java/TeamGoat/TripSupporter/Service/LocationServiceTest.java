//package TeamGoat.TripSupporter.Service;
//
//import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
//import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
//import TeamGoat.TripSupporter.Domain.Entity.Location.Tag;
//import TeamGoat.TripSupporter.Mapper.Location.LocationMapper;
//import TeamGoat.TripSupporter.Repository.Location.LocationRepository;
//import TeamGoat.TripSupporter.Service.Location.LocationServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//public class LocationServiceTest {
//
//    @Mock
//    private LocationRepository locationRepository;
//
//    @Mock
//    private LocationMapper locationMapper;
//
//    @InjectMocks
//    private LocationServiceImpl locationService;
//
//    @BeforeEach
//    void setUp() {
//        // Mock 객체 초기화
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @DisplayName("태그 이름으로 필터링된 장소 목록 조회")
//    @Test
//    void testFindLocationsByTag() {
//        // 태그 데이터 생성
//        Tag tag1 = new Tag(1L, "관광명소");
//
//        // Mock Location 데이터 생성
//        Location location1 = Location.builder()
//                .locationId(1L)
//                .locationName("신사이바시")
//                .build();
//        Location location2 = Location.builder()
//                .locationId(2L)
//                .locationName("우메다 백화점")
//                .build();
//
//        // Mock Repository 반환값 설정
//        when(locationRepository.findLocationsByTagName("관광명소"))
//                .thenReturn(List.of(location1, location2));
//
//        // Mock Mapper 반환값 설정
//        when(locationMapper.toLocationDto(location1)).thenReturn(
//                LocationDto.builder()
//                        .locationId(1L)
//                        .locationName("신사이바시")
//                        .tags(Set.of("관광명소"))
//                        .build()
//        );
//        when(locationMapper.toLocationDto(location2)).thenReturn(
//                LocationDto.builder()
//                        .locationId(2L)
//                        .locationName("우메다 백화점")
//                        .tags(Set.of("관광명소"))
//                        .build()
//        );
//
//        // 서비스 호출
//        List<LocationDto> result = locationService.findLocationsByTag("관광명소");
//
//        // 결과 검증
//        assertEquals(2, result.size(), "필터링된 결과 크기가 올바르지 않습니다.");
//        assertEquals("신사이바시", result.get(0).getLocationName(), "첫 번째 장소 이름이 예상과 다릅니다.");
//        assertEquals("우메다 백화점", result.get(1).getLocationName(), "두 번째 장소 이름이 예상과 다릅니다.");
//    }
//
//
//}
