package TeamGoat.TripSupporter.Controller.Certification;

import TeamGoat.TripSupporter.Service.Certification.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        emailService.sendVerificationEmail(to);
        return "인증 이메일이 발송되었습니다.";
    }
}
