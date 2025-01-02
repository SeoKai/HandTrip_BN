package TeamGoat.TripSupporter.Repository.Favorite;

import TeamGoat.TripSupporter.Domain.Entity.Favorite.UserLocationFavorite;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLocationFavoriteRepository extends JpaRepository<UserLocationFavorite , Long> {

    boolean existsByUserAndLocation(User user, Location location);

    Optional<UserLocationFavorite> findByUserAndLocation(User user, Location location);

    Page<UserLocationFavorite> findByUser(User user, Pageable pageable);

    List<UserLocationFavorite> findByUser(User user);

    long countByLocation_locationId(Long locationId);
}
