package TeamGoat.TripSupporter.Service.Auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupScheduler {

    private final AuthServiceImpl authService;

    // 매일 자정에 만료된 RefreshToken 삭제
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 실행
    public void cleanUpExpiredTokens() {
        try {
            log.info("만료된 RefreshToken 삭제 작업 시작");
            authService.deleteExpiredTokens();
        } catch (Exception e) {
            log.error("만료된 RefreshToken 삭제 작업 실패: {}", e.getMessage());
        }
    }
}
