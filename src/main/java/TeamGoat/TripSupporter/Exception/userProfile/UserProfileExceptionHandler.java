package TeamGoat.TripSupporter.Exception.userProfile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "TeamGoat.TripSupporter.Controller") // 특정 패키지 대상
public class UserProfileExceptionHandler {

    /**
     * UserProfileException 처리
     */
    @ExceptionHandler(UserProfileException.class)
    public ResponseEntity<String> handleUserProfileException(UserProfileException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * IllegalArgumentException 처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * 기타 예상치 못한 예외 처리
     */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGenericException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("UserProfile 처리 중 오류가 발생했습니다.");
//    }
}
