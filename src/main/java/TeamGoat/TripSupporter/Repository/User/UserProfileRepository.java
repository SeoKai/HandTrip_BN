package TeamGoat.TripSupporter.Repository.User;

import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Domain.Entity.User.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    /**
     * 이메일로 사용자 프로필 조회
     *
     * @param email 조회하려는 사용자의 이메일
     * @return 해당 이메일을 가진 사용자의 프로필 객체(Optional)
     */
    Optional<UserProfile> findByUser_UserEmail(String email);

    /**
     * 닉네임 중복 여부 확인
     * @param nickname 사용자의 닉네임
     * @return 닉네임이 이미 존재하면 true, 그렇지 않으면 false
     */
    boolean existsByNickname(String nickname);








}
