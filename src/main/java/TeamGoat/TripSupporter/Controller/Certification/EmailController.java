package TeamGoat.TripSupporter.Controller.Certification;

import TeamGoat.TripSupporter.Service.Certification.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
@Slf4j
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody Map<String, String> payload) {
        log.info("메일 전송");
        String userEmail = payload.get("userEmail");
        String mode = payload.get("mode");
        String phone = payload.get("userPhone");
        log.info("payload 확인 userEmail : {}, mode : {}, phone : {}", userEmail, mode, phone);
        emailService.sendVerificationEmail(userEmail,phone,mode);
        return "이메일이 발송되었습니다.";
    }

    @PostMapping("/verify")
    public String verifyCode(@RequestBody Map<String, String> payload) {
        String userEmail = payload.get("userEmail");
        String code = payload.get("code");
        boolean isVerified = emailService.verifyCode(userEmail, code);
        if (isVerified) {
            return "인증에 성공했습니다.";
        } else {
            return "인증에 실패했습니다.";
        }
    }
}
