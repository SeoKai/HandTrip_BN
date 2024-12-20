package TeamGoat.TripSupporter.Repository.Location;

import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    // regionName을 기준으로 Region 조회
    Optional<Region> findByRegionName(String regionName);
}
