package TeamGoat.TripSupporter.Repository.Auth;

import TeamGoat.TripSupporter.Domain.Entity.Auth.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    // Refresh 토큰으로 사용자 이메일 찾기
    Optional<AuthToken> findByRefreshToken(String refreshToken);

    // 사용자 이메일로 Refresh 토큰 삭제
    void deleteByUserEmail(String userEmail);

    Optional<AuthToken> findByUserEmail(String userEmail);

    // 만료된 RefreshToken 검색
    @Query("SELECT t FROM AuthToken t WHERE t.expiration < :currentTime")
    List<AuthToken> findExpiredTokens(@Param("currentTime") long currentTime);
}
