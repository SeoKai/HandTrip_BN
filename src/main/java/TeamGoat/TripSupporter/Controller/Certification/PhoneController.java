package TeamGoat.TripSupporter.Controller.Certification;

import TeamGoat.TripSupporter.Domain.Dto.Certification.VerificationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verify")
@Slf4j
@RequiredArgsConstructor
public class PhoneController {

    @PostMapping("/phone")
    public ResponseEntity<VerificationResponseDto> verifyPhone(@RequestBody String identityVerificationId) {
        VerificationResponseDto response = new VerificationResponseDto();
        log.info("Verifying");
        // 여기에 본인 인증 로직을 추가하고, 성공/실패 여부에 따라 response 객체에 값을 설정합니다.
        // 예시로는 성공한 경우 값을 넣습니다.
        response.setIdentityVerificationId(identityVerificationId);
        response.setCode("200");  // 성공적인 처리
        response.setMessage("Phone verification is complete!");

        // 성공적인 응답을 반환
        return ResponseEntity.ok(response);
    }

}
