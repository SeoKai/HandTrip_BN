//package TeamGoat.TripSupporter.Controller.Ai;
//
//import TeamGoat.TripSupporter.Service.Planner.PlannerService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/places")
//@RequiredArgsConstructor
//public class PlaceController {
//
//    private final PlannerService plannerService;
//
//    @GetMapping("/random")
//    public ResponseEntity<?> getRandomPlaces(@RequestParam int count) {
//        try {
//            List<PlaceDto> places = placeService.getRandomPlaces(count);
//            return ResponseEntity.ok(places);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
//        }
//    }
//}
