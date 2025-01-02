package TeamGoat.TripSupporter.Service.Certification;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@RequiredArgsConstructor
public class PhoneService {

    private final SecureRandom random = new SecureRandom();
    private final StringRedisTemplate redisTemplate;

    /**
     * 인증번호 생성 메서드
     * @return 생성된 인증번호
     */
    public String generateVerificationCode() {
        int code = 100000 + random.nextInt(900000); // 6자리 인증번호 생성
        return String.valueOf(code);
    }

    @Transactional
    public Message sendVerificationMail(String phoneNumber){

        // 인증번호 난수 생성
        String code = generateVerificationCode();


        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("010-5045-0503");
        message.setTo(phoneNumber);
        message.setText(code);


        // Redis에 인증번호 저장 (유효시간: 5분)
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(phoneNumber, code, 5, TimeUnit.MINUTES);
        long redisTemplateTime = System.currentTimeMillis();

        return message;

    }

    /**
     * 인증번호 확인 메서드
     *
     * @param phoneNumber 입력된 이메일
     * @param code  입력된 인증번호
     * @return 인증 성공 여부
     */
    public boolean verifyCode(String phoneNumber, String code) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String storedCode = valueOperations.get(phoneNumber);

        return storedCode != null && storedCode.equals(code);
    }
}
