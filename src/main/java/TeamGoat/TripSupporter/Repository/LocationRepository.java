package TeamGoat.TripSupporter.Repository;


import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByRegionRegionName(String regionName);
}
