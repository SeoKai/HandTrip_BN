// Updated AiController after reviewing the project files
package TeamGoat.TripSupporter.Controller.Ai;

import TeamGoat.TripSupporter.Domain.Dto.Ai.AiUserDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Mapper.Location.LocationMapper;
import TeamGoat.TripSupporter.Repository.Location.LocationRepository;
import TeamGoat.TripSupporter.Service.Ai.AiModelIntegrationService;
import lombok.RequiredArgsConstructor;
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
public class AiController {

    private final AiRecommendationService aiRecommendationService;
    private final AiModelIntegrationService aiModelIntegrationService;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @PostMapping("/rating")
    public ResponseEntity<Void> submitRating(@RequestBody AiUserDto aiUserDto) {
        // 평점 데이터를 DB에 저장
        aiRecommendationService.saveRecommendation(aiUserDto);
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
        List<Location> locations = locationRepository.findAll(); // DB에서 모든 장소 가져오기
        Collections.shuffle(locations); // 랜덤화
        List<LocationResponseDto> randomPlaces = locations.stream()
                .limit(5) // 상위 5개만 가져오기
                .map(locationMapper::toResponseDto) // Location -> LocationResponseDto 변환
                .collect(Collectors.toList());
        return ResponseEntity.ok(randomPlaces); // 변환된 데이터를 반환
    }
}


