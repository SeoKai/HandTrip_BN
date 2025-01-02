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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    @PostMapping("/verify")
    public ResponseEntity<List<LocationDto>> verifyAndFetchRecommendations(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            List<LocationDto> locationDtos;

            if (token == null || token.isEmpty()) {
                log.info("비로그인 상태. 랜덤 추천 (음식 제외) 반환.");

                // 랜덤으로 5개 장소 가져오기 (음식 제외)
                List<Location> randomLocations = locationRepository.findRandomTopLocationsExcludingFood();
                locationDtos = randomLocations.stream()
                        .map(locationMapper::toLocationDto)
                        .collect(Collectors.toList());
            } else {
                String email = jwtTokenProvider.extractUserEmail(token.replace("Bearer ", ""));
                Long userId = aiRecommendationService.getUserIdByEmail(email);

                // Flask 통신 결과 가져오기
                List<String> recommendations = null;
                try {
                    recommendations = aiModelIntegrationService.getRecommendationsFromFlask(userId);
                } catch (Exception flaskException) {
                    log.warn("Flask 통신 오류 발생: {}", flaskException.getMessage());
                }

                if (recommendations != null && !recommendations.isEmpty()) {
                    List<Location> locations = recommendations.stream()
                            .map(locationRepository::findByLocationName)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(Collectors.toList());

                    locationDtos = locations.stream()
                            .map(locationMapper::toLocationDto)
                            .collect(Collectors.toList());
                } else {
                    log.info("추천 데이터가 없음 또는 통신 오류. 랜덤 추천 (음식 제외) 반환.");
                    List<Location> randomLocations = locationRepository.findRandomTopLocationsExcludingFood();
                    locationDtos = randomLocations.stream()
                            .map(locationMapper::toLocationDto)
                            .collect(Collectors.toList());
                }
            }

            return ResponseEntity.ok(locationDtos);
        } catch (Exception e) {
            log.error("Error occurred: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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


