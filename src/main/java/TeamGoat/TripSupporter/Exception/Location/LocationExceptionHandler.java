package TeamGoat.TripSupporter.Exception.Location;


import TeamGoat.TripSupporter.Controller.Location.LocationController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = LocationController.class)
public class LocationExceptionHandler {

    @ExceptionHandler(IllegalLocationIdException.class)
    public ResponseEntity<String> handleIllegalLocationIdException(IllegalLocationIdException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<String> handleLocationNotFoundException(LocationNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(LocationMappingException.class)
    public ResponseEntity<String> handleLocationMappingException(LocationMappingException e){
        return ResponseEntity.status(422).body(e.getMessage());
    }
    @ExceptionHandler(LocationInvalidDistanceException.class)
    public ResponseEntity<String> handleLocationInvalidDistanceException(LocationInvalidDistanceException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(LocationInvalidLatitudeException.class)
    public ResponseEntity<String> handleLocationInvalidLatitudeException(LocationInvalidLatitudeException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(LocationInvalidLongitudeException.class)
    public ResponseEntity<String> handleLocationInvalidLongitudeException(LocationInvalidLongitudeException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(LocationLatAndLonNullException.class)
    public ResponseEntity<String> handleLocationLatAndLonNullException(LocationLatAndLonNullException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(LocationPageRequestNullException.class)
    public ResponseEntity<String> handleLocationPageRequestNullException(LocationPageRequestNullException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(LocationRegionIdException.class)
    public ResponseEntity<String> handleLocationRegionIdException(LocationRegionIdException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(LocationSortNullException.class)
    public ResponseEntity<String> handleLocationSortNullException(LocationSortNullException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(LocationInvalidTagNamesException.class)
    public ResponseEntity<String> handleLocationInvalidTagNamesException(LocationInvalidTagNamesException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

}
