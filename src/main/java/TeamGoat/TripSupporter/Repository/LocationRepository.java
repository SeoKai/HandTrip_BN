package TeamGoat.TripSupporter.Repository;

import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByRegionRegionId(Long regionId); // 특정 지역의 관광지 목록 조회
}