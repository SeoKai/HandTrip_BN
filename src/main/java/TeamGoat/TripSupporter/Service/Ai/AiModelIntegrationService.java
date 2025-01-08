package TeamGoat.TripSupporter.Service.Ai;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiModelIntegrationService {

    private final RestTemplate restTemplate;

    public AiModelIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> getRecommendationsFromFlask(Long userId) {
        String flaskUrl = "http://localhost:5000/recommend";

        // 요청 본문 (JSON 데이터)
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", userId);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // HTTP 요청 생성
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // Flask API 호출
            ResponseEntity<List> response = restTemplate.postForEntity(flaskUrl, requestEntity, List.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // Flask 응답 데이터 반환
                return (List<String>) response.getBody();
            } else {
                throw new RuntimeException("Flask 서버 응답 오류: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Flask와 통신 중 오류 발생: " + e.getMessage(), e);
        }
    }
}
