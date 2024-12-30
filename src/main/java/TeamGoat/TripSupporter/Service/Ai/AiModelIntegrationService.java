package TeamGoat.TripSupporter.Service.Ai;

import TeamGoat.TripSupporter.Domain.Dto.Ai.AiUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class AiModelIntegrationService {

    private final RestTemplate restTemplate;

    public List<String> getRecommendationsFromFlask(Long userId) {
        String flaskUrl = "http://localhost:5000/recommend";

        // 요청 데이터 설정
        Map<String, Long> payload = new HashMap<>();
        payload.put("user_id", userId);

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 엔티티 생성
        HttpEntity<Map<String, Long>> requestEntity = new HttpEntity<>(payload, headers);

        try {
            // Flask 서버로 POST 요청
            ResponseEntity<List> response = restTemplate.postForEntity(flaskUrl, requestEntity, List.class);
            return response.getBody(); // 추천 결과 반환
        } catch (Exception e) {
            throw new RuntimeException("Flask 서버와 통신 중 오류 발생: " + e.getMessage(), e);
        }
    }
}

