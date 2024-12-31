package TeamGoat.TripSupporter.Repository;

import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Repository.Location.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void testFindByLocationName() {
        // Given: 테스트 데이터 삽입
        String locationName = "교토부립식물원";
        Location location = Location.builder()
                .locationName(locationName)
                .description("교토의 아름다운 식물원")
                .latitude(35.0391)
                .longitude(135.7578)
                .build();
        locationRepository.save(location);

        // When: findByLocationName 호출
        Optional<Location> result = locationRepository.findByLocationName(locationName);

        // Then: 결과 검증
        assertThat(result).isPresent();
        assertThat(result.get().getLocationName()).isEqualTo(locationName);
    }
}
