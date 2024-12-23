package TeamGoat.TripSupporter.Repository.User;


import TeamGoat.TripSupporter.Domain.Entity.User.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
    UserProfile findByUser_UserId(Long userId);

    /**
     * 이메일을 기반으로 유저 프로필을 조회하는 메서드.
     *
     * @param email 조회할 사용자 이메일
     * @return 조회된 유저 프로필
     */
    Optional<UserProfile> findByUser_UserEmail(String email);

    /**
     * 닉네임 중복 확인 메서드.
     * 닉네임이 데이터베이스에 존재하는지 확인.
     *
     * @param nickname 확인할 닉네임
     * @return 닉네임이 이미 존재하면 true, 아니면 false
     */
    boolean existsByUserNickname(String nickname);

}
