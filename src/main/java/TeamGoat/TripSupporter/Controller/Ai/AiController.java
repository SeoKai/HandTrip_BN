// Updated AiController after reviewing the project files
package TeamGoat.TripSupporter.Controller.Ai;

import TeamGoat.TripSupporter.Config.auth.JwtTokenProvider;
import TeamGoat.TripSupporter.Domain.Dto.Ai.AiUserDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Mapper.Location.LocationMapper;
import TeamGoat.TripSupporter.Repository.Location.LocationRepository;
import TeamGoat.TripSupporter.Service.Ai.AiModelIntegrationService;
import TeamGoat.TripSupporter.Service.User.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import TeamGoat.TripSupporter.Service.Ai.AiRecommendationService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@Slf4j
public class AiController {

    private final AiRecommendationService aiRecommendationService;
    private final AiModelIntegrationService aiModelIntegrationService;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/rating")
    public ResponseEntity<Void> submitRatings(
            @RequestHeader("Authorization") String authorization,
            @RequestBody List<AiUserDto> ratings) {

        // JWT에서 이메일 추출
        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);

        log.info("Extracted userEmail from JWT: {}", userEmail);
        log.info("저장할 추천 데이터: {}", ratings);

        // 이메일로 userId 조회
        Long userId = aiRecommendationService.getUserIdByEmail(userEmail);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 이메일이 유효하지 않음
        }

        log.info("UserId for email {}: {}", userEmail, userId);

        // userId를 각 DTO에 추가
        ratings.forEach(rating -> {
            rating.setUserId(userId); // 이메일 기반 userId 설정
        });

        // 점수 저장
        aiRecommendationService.saveRecommendations(ratings);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/recommendations/{userId}")
    public ResponseEntity<List<String>> fetchRecommendations(@PathVariable Long userId) {
        try {
            // Flask 서버에서 추천 데이터 가져오기
            List<String> recommendations = aiModelIntegrationService.getRecommendationsFromFlask(userId);
            System.out.println(recommendations);
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/random-places")
    public ResponseEntity<List<LocationResponseDto>> getRandomPlaces() {
        List<Location> locations = locationRepository.findRandomPlacesExcludingFood(); // 음식 태그 제외
        List<LocationResponseDto> responseDtos = locations.stream()
                .map(locationMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }
}


