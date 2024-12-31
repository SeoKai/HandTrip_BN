package TeamGoat.TripSupporter.Repository.Ai;

import TeamGoat.TripSupporter.Domain.Entity.Ai.AiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AiUserRepository extends JpaRepository<AiUser, Long> {

    /**
     * 특정 사용자 ID로 추천 데이터를 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 해당 사용자의 추천 데이터 리스트
     */
    List<AiUser> findByUser_UserId(Long userId);

    /**
     * 특정 장소 ID로 추천 데이터를 조회합니다.
     *
     * @param locationId 장소 ID
     * @return 해당 장소의 추천 데이터 리스트
     */
    List<AiUser> findByLocation_LocationId(Long locationId);
}
