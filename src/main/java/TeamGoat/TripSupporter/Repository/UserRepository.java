package TeamGoat.TripSupporter.Repository;

import TeamGoat.TripSupporter.Domain.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 이메일로 사용자 조회
     *
     * @param email 조회하려는 사용자의 이메일
     * @return 해당 이메일을 가진 사용자의 Optional 객체
     */
    Optional<User> findByEmail(String email);

    /**
     * 이메일 중복 여부 확인
     *
     * @param email 중복 확인하려는 이메일
     * @return 이메일이 이미 존재하면 true, 그렇지 않으면 false
     */
    boolean existsByUserEmail(String email);



    Optional<User> findByEmailAndPhone(String email, String phone);
    /**
     * 닉네임 중복 확인 메서드.
     * 닉네임이 데이터베이스에 존재하는지 확인.
     *
     * @param nickname 확인할 닉네임
     * @return 닉네임이 이미 존재하면 true, 아니면 false
     */
    boolean existsByNickname(String nickname);
}
