package TeamGoat.TripSupporter.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(assignableTypes = TeamGoat.TripSupporter.Controller.User.UserController.class)
public class UserExceptionHandler implements ExceptionHandler{

    @Override
    public Object exceptionHandler(Exception exception) {
        return null;
    }

   @org.springframework.web.bind.annotation.ExceptionHandler
    public Object DuplicateEmailExceptionHandler(DuplicateEmailException exception) {
       return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public Object NullPointerException(NullPointerException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
