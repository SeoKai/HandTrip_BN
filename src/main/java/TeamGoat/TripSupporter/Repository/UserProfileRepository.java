package TeamGoat.TripSupporter.Repository;

import TeamGoat.TripSupporter.Domain.Entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    // 기본 CRUD는 JpaRepository가 제공
}
