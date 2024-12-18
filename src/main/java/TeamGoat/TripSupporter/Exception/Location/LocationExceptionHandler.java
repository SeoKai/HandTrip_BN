package TeamGoat.TripSupporter.Exception.Location;


import TeamGoat.TripSupporter.Controller.Location.LocationController;
import TeamGoat.TripSupporter.Exception.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(assignableTypes = LocationController.class)
public class LocationExceptionHandler implements ExceptionHandler {
    @Override
    public Object exceptionHandler(Exception exception) {
        return null;
    }

    public ResponseEntity<String> handleLocationNullException(LocationNullException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    public ResponseEntity<String> handleLocationMappingException(LocationMappingException e){
        return ResponseEntity.status(422).body(e.getMessage());
    }

    public ResponseEntity<String> handleLocationInvalidDistanceException(LocationInvalidDistanceException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    public ResponseEntity<String> handleLocationInvalidLatitudeException(LocationInvalidLatitudeException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    public ResponseEntity<String> handleLocationInvalidLongitudeException(LocationInvalidLongitudeException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    public ResponseEntity<String> handleLocationLatAndLonNullException(LocationLatAndLonNullException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    public ResponseEntity<String> handleLocationPageRequestNullException(LocationPageRequestNullException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    public ResponseEntity<String> handleLocationRegionIdException(LocationRegionIdException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    public ResponseEntity<String> handleLocationSearchKeywordNullException(LocationSearchKeywordNullException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    public ResponseEntity<String> handleLocationSortNullException(LocationSortNullException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    public ResponseEntity<String> handleLocationInvalidTagNamesException(LocationInvalidTagNamesException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

}
