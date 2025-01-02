package TeamGoat.TripSupporter.Repository.Location;

import TeamGoat.TripSupporter.Domain.Entity.Location.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository <Tag,Long>{


}
