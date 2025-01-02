package TeamGoat.TripSupporter.Controller.Favorite;


import TeamGoat.TripSupporter.Config.auth.JwtTokenProvider;
import TeamGoat.TripSupporter.Controller.Location.Util.LocationControllerValidator;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationResponseDto;
import TeamGoat.TripSupporter.Exception.UserNotFoundException;
import TeamGoat.TripSupporter.Service.Favorite.UserLocationFavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locationFavorite")
@RequiredArgsConstructor
@Slf4j
public class UserLocationFavoriteController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserLocationFavoriteService userLocationFavoriteService;

    @PostMapping("/add")
    public ResponseEntity<String> addFavoriteLocation(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, Long> body) {
        log.info("Add favorite location");
        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);

        Long locationId = body.get("locationId");

        LocationControllerValidator.validateLocationId(locationId);

        userLocationFavoriteService.addFavoriteLocation(userEmail, locationId);

        return ResponseEntity.ok("즐겨찾기가 성공적으로 추가되었습니다.");

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFavoriteLocation(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, Long> body) {
        log.info("Delete favorite location");
        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);

        Long locationId = body.get("locationId");

        LocationControllerValidator.validateLocationId(locationId);

        userLocationFavoriteService.deleteFavoriteLocation(userEmail, locationId);

        return ResponseEntity.ok("즐겨찾기가 성공적으로 삭제되었습니다.");
    }

    /**
     * 사용자의 즐겨찾기한 여행지 목록을 조회합니다.
     * 페이징 처리 및 정렬이 적용된 여행지 목록을 반환합니다.
     *
     * @param authorization HTTP 헤더에서 Authorization 값을 받아 사용자의 이메일을 추출합니다.
     * @param page 조회할 페이지 번호 (0부터 시작)
     * @param pageSize 한 페이지당 항목 수
     * @param sortValue 정렬 기준 필드
     * @param sortDirection 정렬 방향 ("ASC" 또는 "DESC")
     * @return ResponseEntity<Page<LocationResponseDto>> 페이징 처리된 즐겨찾기 여행지 목록
     *
     * @throws UserNotFoundException 사용자가 존재하지 않으면 발생
     */
    @GetMapping("/userFavorites")
    public ResponseEntity<Page<LocationResponseDto>> getUserFavoriteLocations(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("sortValue") String sortValue,
            @RequestParam("sortDirection") String sortDirection) {

        log.info("사용자 즐겨찾기 여행지 목록 조회 요청");

        // Authorization 헤더에서 사용자 이메일 추출
        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);

        // 사용자 즐겨찾기 목록 조회
        Page<LocationResponseDto> favoriteLocations = userLocationFavoriteService
                .getUserFavoriteLocations(userEmail, page, pageSize, sortValue, sortDirection);

//        List<LocationResponseDto> favoriteLocations = userLocationFavoriteService.getUserFavoriteLocations(userEmail, page, pageSize, sortValue, sortDirection);

        return ResponseEntity.ok(favoriteLocations);  // 페이징된 즐겨찾기 여행지 목록 반환
    }

    /**
     * 특정 여행지를 즐겨찾기한 사용자 수 조회
     *
     * @param locationId 즐겨찾기한 여행지의 ID
     * @return 특정 여행지를 즐겨찾기한 사용자 수
     */
    @GetMapping("/count/{locationId}")
    public ResponseEntity<Long> getFavoriteUserCountForLocation(@PathVariable Long locationId) {
        log.info("특정 여행지를 즐겨찾기한 사용자 수 조회");
        LocationControllerValidator.validateLocationId(locationId);
        long userCount = userLocationFavoriteService.getFavoriteUserCountForLocation(locationId);

        return ResponseEntity.ok(userCount);
    }

}
