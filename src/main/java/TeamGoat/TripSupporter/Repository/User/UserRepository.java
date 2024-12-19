package TeamGoat.TripSupporter.Repository.User;

import TeamGoat.TripSupporter.Domain.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 이메일로 사용자 조회
     *
     * @param email 조회하려는 사용자의 이메일
     * @return 해당 이메일을 가진 사용자의 Optional 객체
     */
    Optional<User> findByUserEmail(String email);

    /**
     * 이메일 중복 여부 확인
     *
     * @param email 중복 확인하려는 이메일
     * @return 이메일이 이미 존재하면 true, 그렇지 않으면 false
     */
    boolean existsByUserEmail(String email);

    Optional<User> findByUserProfile_PhoneNumber(String phoneNumber);








}
