package TeamGoat.TripSupporter.Controller.Planner;

import TeamGoat.TripSupporter.Config.auth.JwtTokenProvider;
import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
import TeamGoat.TripSupporter.Service.Planner.PlannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planner")
@RequiredArgsConstructor
@Slf4j
public class PlannerController {

    private final PlannerService plannerService;
    private final JwtTokenProvider jwtTokenProvider;

    // 플래너 저장
    @PostMapping("/save")
    public ResponseEntity<Long> savePlanner(
            @RequestHeader("Authorization") String authorization,
            @RequestBody PlannerDto plannerDto) {

        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);
        plannerDto.setUserEmail(userEmail); // PlannerDto에 이메일 설정
        Long plannerId = plannerService.savePlanner(plannerDto);

        return ResponseEntity.ok(plannerId);
    }

    // 플랜 아이디 기반 플래너 목록 조회
    @GetMapping("/{id}")
    public ResponseEntity<PlannerDto> getPlannerDetails(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("id") Long id) {

        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);

        PlannerDto planner = plannerService.getPlannerDetails(id);

        if (planner != null && planner.getUserEmail().equals(userEmail)) {
            return ResponseEntity.ok(planner);
        } else {
            return ResponseEntity.status(403).build(); // 권한 없음
        }
    }

    // 사용자 이메일 기반 조회
    @GetMapping("/user/plans")
    public ResponseEntity<List<PlannerDto>> getAllPlansByEmail(@RequestHeader("Authorization") String authorization) {

        // Authorization 헤더에서 토큰 추출
        log.info("getAllPlansByEmail Method invoked - token: {}", authorization);
        String token = authorization.replace("Bearer ", "");

        // 토큰에서 사용자 이메일 추출
        String userEmail = jwtTokenProvider.extractUserEmail(token);

        // 이메일로 플래너 조회
        List<PlannerDto> plannerDtos = plannerService.getAllPlansByEmail(userEmail);

        // 로깅 및 결과 반환
        log.info("사용자 email: {}, 조회된 PlannerDtos: {}", userEmail, plannerDtos);
        return ResponseEntity.ok(plannerDtos);
    }

    // 플래너 수정 (id를 RequestBody로 받음)
    @PutMapping("/update")
    public ResponseEntity<Void> updatePlanner(
            @RequestHeader("Authorization") String authorization,
            @RequestBody PlannerDto plannerDto) {

        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);

        log.info("Received PlannerDto {}", plannerDto);
        log.info("userEmail {}", userEmail);

        if (plannerDto.getPlannerId() == null) {
            throw new IllegalArgumentException("Panner Id IS NULL");
        }

        if (!plannerDto.getUserEmail().equals(userEmail)) {
            return ResponseEntity.status(403).build(); // 권한 없음
        }

        plannerService.updatePlanner(plannerDto.getPlannerId(), plannerDto);
        return ResponseEntity.ok().build(); // 수정 성공 시 200 OK 반환
    }

    // 플래너 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanner(
            @RequestHeader("Authorization") String authorization,
            @PathVariable(name = "id", required = true) Long id) {

        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);

        PlannerDto planner = plannerService.getPlannerDetails(id);

        log.info("userEmail {}", userEmail);
        log.info("삭제 요청힌 planner ID: {}", id);

        if (planner == null || !planner.getUserEmail().equals(userEmail)){
            return ResponseEntity.status(403).build(); // 권한 없음
        }

        plannerService.deletePlanner(id);
        return ResponseEntity.noContent().build();
    }
}
