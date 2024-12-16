package TeamGoat.TripSupporter.Repository;

import TeamGoat.TripSupporter.Domain.Entity.Location.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findByRegionName(String regionName); // 지역 이름으로 조회
}