package TeamGoat.TripSupporter.Service.Certification;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SecureRandom random = new SecureRandom();


    /**
     * 인증번호 생성 메서드
     * @return 생성된 인증번호
     */
    public String generateVerificationCode() {
        int code = 100000 + random.nextInt(900000); // 6자리 인증번호 생성
        return String.valueOf(code);
    }

    public void sendVerificationEmail(String toEmail) {
        String subject = "이메일 인증 코드";
        String verificationCode = generateVerificationCode();
        String body = "<h1>인증 코드</h1><p>다음 코드를 입력하세요: <strong>" + verificationCode + "</strong></p>";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom("handtrip2024@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(mimeMessage);
            System.out.println("이메일이 성공적으로 전송되었습니다.");

            // 이후 인증번호를 DB 또는 Redis에 저장
        } catch (MessagingException e) {
            System.err.println("이메일 전송 중 오류가 발생했습니다: " + e.getMessage());
        }

    }
}