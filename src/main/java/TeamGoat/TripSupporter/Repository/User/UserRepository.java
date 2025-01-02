package TeamGoat.TripSupporter.Repository.User;


import TeamGoat.TripSupporter.Domain.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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
     * 전화번호로 사용자 조회
     *
     * @param phone 조회하려는 사용자의 전화번호
     * @return 해당 이메일을 가진 사용자의 Optional 객체
     */
    List<User> findByUserPhone(String phone);


    /**
     * 이메일 중복 여부 확인
     *
     * @param email 중복 확인하려는 이메일
     * @return 이메일이 이미 존재하면 true, 그렇지 않으면 false
     */
    boolean existsByUserEmail(String email);


    /**
     * 등록된 Email 과 비밀번호 가 일치하는 지 확인
     *
     * @param email    확인 할 이메일
     * @param password 확인 할 비밀번호
     * @return 등록 된 이메일 과 비밀번호가 일치하면 true, 불일치 하면 false
     */


    Optional<User> findByUserEmailAndUserPhone(String email, String password);


    /**
     * 이메일과 일치하는 유저 아이디를 받아오는 메서드
     *
     * @param email 확인할 이메일
     * @return 이메일과 일치하는 userId
     */
    @Query("SELECT u.userId FROM User u WHERE u.userEmail = :email")
    Optional<Long> findUserIdByEmail(@Param("email") String email);
}
