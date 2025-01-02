package TeamGoat.TripSupporter.Exception.Review;

import TeamGoat.TripSupporter.Controller.Review.ReviewController;
import TeamGoat.TripSupporter.Exception.IllegalPageRequestException;
import TeamGoat.TripSupporter.Exception.Location.LocationNotFoundException;
import TeamGoat.TripSupporter.Exception.UserNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * ReviewController에서 발생하는 예외를 처리하는 전역 예외 핸들러
 * ControllerAdvice를 사용하여 예외 처리 로직을 중앙화
 */
@ControllerAdvice(assignableTypes = ReviewController.class)
public class ReviewExceptionHandler{



    /**
     * ReviewNotFoundException 처리
     * @param e 리뷰를 찾지 못했을 때 발생하는 예외
     * @return 404 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleReviewNotFoundException(ReviewNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
    /**
     * LocationNotFoundException 처리
     * @param e Location을 찾지 못했을때 발생하는 예외
     * @return 404 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleLocationNotFoundException(LocationNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
    /**
     * UserNotFoundException 처리
     * @param e User를 찾지 못했을때 발생하는 예외
     * @return 404 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
    /**
     * ReviewDtoNullException 처리
     * @param e 리뷰 DTO가 null인 경우 발생하는 예외
     * @return 400 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleReviewDtoNullException(ReviewDtoNullException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
    /**
     * ReviewCommentNullException 처리
     * @param e 리뷰 본문이 null인 경우 발생하는 예외
     * @return 400 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleReviewCommentNullException(ReviewCommentNullException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
    /**
     * ReviewRatingNullException 처리
     * @param e 리뷰 별점이 null인 경우 발생하는 예외
     * @return 400 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleReviewRatingNullException(ReviewRatingNullException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
    /**
     * ReviewAuthorMismatchException 처리
     * @param e 리뷰작정자와 로그인중인 사용자가 일치하지 않을 경우 발생하는 예외
     * @return 403 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleReviewAuthorMismatchException(ReviewAuthorMismatchException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }
    /**
     * ReviewStatusMismatchException 처리
     * @param e 리뷰상태가 일치하지 않을 경우 발생하는 예외
     * @return 400 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleReviewStatusMismatchException(ReviewStatusMismatchException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
    /**
     * MismatchedIdsException 처리
     * @param e 두 Id의 비교값이 다를때 발생하는 예외
     * @return 400 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleMismatchedIdsException(MismatchedException e) {
        return ResponseEntity.status(400).body("ID 불일치: " + e.getMessage());
    }
    /**
     * ReviewException 처리
     * @param e 리뷰와 관련된 예외(데이터베이스 오류, 잘못된 데이터 입력, 기타 예상치 못한 예외 등)
     * @return 400 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleReviewException(ReviewException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }
    /**
     * IllegalArgumentException 처리
     * @param e 잘못된입력값을 받았을때 발생하는 예외
     * @return 400 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(400).body("잘못된 입력: " + e.getMessage());
    }
    /**
     * ConstraintViolationException 처리
     * @param e DB 제약조건을 위반했을때 발생하는 예외
     * @return 400 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(400).body("제약 조건 위반: " + e.getMessage());
    }

    /**
     * DataAccessException 처리
     * @param e DB통신에 문제가 생겼을때 발생하는 예외
     * @return 400 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleDataAccessException(DataAccessException e) {
        return ResponseEntity.status(500).body("데이터베이스 오류: " + e.getMessage());
    }



    /**
     * DataAccessException 처리
     * @param e 페이징객체 생성에 문제가 생겼을때 발생하는 예외
     * @return 400 상태 코드와 예외 메시지
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleIllegalPageRequestException(IllegalPageRequestException e) {
        return ResponseEntity.status(400).body("페이징 객체 생성 오류: " + e.getMessage());
    }


}
