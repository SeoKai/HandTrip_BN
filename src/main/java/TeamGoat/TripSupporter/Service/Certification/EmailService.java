package TeamGoat.TripSupporter.Service.Certification;


import TeamGoat.TripSupporter.Domain.Entity.Certification.VerificationCode;
import TeamGoat.TripSupporter.Repository.Certification.VerificationCodeRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SecureRandom random = new SecureRandom();
    private final StringRedisTemplate redisTemplate;
    private final SimpleMailMessage message;

    /**
     * 인증번호 생성 메서드
     * @return 생성된 인증번호
     */
    public String generateVerificationCode() {
        int code = 100000 + random.nextInt(900000); // 6자리 인증번호 생성
        return String.valueOf(code);
    }

    /**
     * 인증번호 이메일 발송 메서드
     * @param toEmail 이메일주소(받는사람)
     */
    public void sendVerificationEmail(String toEmail) {
        String subject = "이메일 인증 코드";
        String verificationCode = generateVerificationCode();
        String body = "인증 코드\n 다음 코드를 입력하세요: " + verificationCode;

        try {

            long startTime = System.currentTimeMillis();

            // Redis에 인증번호 저장 (유효시간: 5분)
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(toEmail, verificationCode, 5, TimeUnit.MINUTES);
            long redisTemplateTime = System.currentTimeMillis();
            log.info("redisTemplate 작동 시간: {}ms", redisTemplateTime - startTime);


            // 이메일 발송
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            long MimeMessageTime = System.currentTimeMillis();
            log.info("MimeMessage 작동 시간: {}ms", MimeMessageTime - startTime);


            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            long helperTime = System.currentTimeMillis();
            log.info("message객체에 값 넣는대 걸리는 시간: {}ms", helperTime - startTime);


            mailSender.send(message);
            System.out.println("인증번호가 이메일로 전송되었습니다.");
            long endTime = System.currentTimeMillis();
            log.info("이메일 발송 시간: {}ms", endTime - startTime);

        } catch (Exception e) {
            System.err.println("이메일 전송 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 인증번호 확인 메서드
     *
     * @param email 입력된 이메일
     * @param code  입력된 인증번호
     * @return 인증 성공 여부
     */
    public boolean verifyCode(String email, String code) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String storedCode = valueOperations.get(email);

        return storedCode != null && storedCode.equals(code);
    }
}