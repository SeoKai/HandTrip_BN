package TeamGoat.TripSupporter.Service.Review.Util;

import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewDto;
import TeamGoat.TripSupporter.Domain.Entity.Review.Review;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Exception.IllegalPageRequestException;
import TeamGoat.TripSupporter.Exception.Location.LocationNotFoundException;
import TeamGoat.TripSupporter.Exception.Review.*;
import TeamGoat.TripSupporter.Exception.UserNotFoundException;
import org.springframework.data.domain.Pageable;

public class ReviewServiceValidator {


    /**
     * reviewId null 체크
     * @param reviewId
     */
    public static void validateReviewId(Long reviewId) {
        if (reviewId == null) {
            throw new ReviewNotFoundException("reviewId가 null이기때문에 review를 찾을 수 없습니다.");
        }
    }

    public static void validateCheck(int check){
        if (check < 1 || check > 3){
            throw new IllegalArgumentException("check 값은 1, 2, 3 이어야합니다.");
        }
    }

    /**
     * user null 체크
     * @param user
     */
    public static void validateUser(User user) {
        if (user == null) {
            throw new UserNotFoundException("사용자 정보를 찾을 수 없습니다.");
        }
    }

    /**
     * loacationId null 체크
     * @param locationId
     */
    public static void validateLocationId(Long locationId) {
        if (locationId == null) {
            throw new LocationNotFoundException("locationId가 null이기때문에 location을 찾을 수 없습니다.");
        }
    }

    public static void validateUserId(Long userId) {
        if (userId == null) {
            throw new UserNotFoundException("userId가 null이기때문에 user를 찾을 수 없습니다.");
        }
    }

    public static void validateUserEmail(String userEmail) {
        if (userEmail == null) {
            throw new IllegalArgumentException("userEmail이 null입니다.");
        }
    }

    /**
     * 작성자와 사용자가 일치하는지 확인함.
     * @param user 사용자 ( view로부터 입력받은 userId 또는 reviewDto.getUserid() )
     * @param author 작성자 ( repository에서 불러온 userId 또는 review.getUser().getUserId() )
     */
    public static void validateUserAndAuthor(User user, User author){
        if(!user.getUserId().equals(author.getUserId())){
            throw new ReviewAuthorMismatchException("작성자와 사용자가 일치하지 않습니다.");
        }

    }

    /**
     * review의 status필드가 active인지 확인합니다
     * @param review
     */
    public static void isReviewStatusActive(Review review){
        if(!review.isActive()){
            throw new ReviewStatusMismatchException("이 Review는 Active 상태가 아닙니다." + review.getReviewStatus());
        }
    }
    /**
     * review의 status필드가 delete인지 확인합니다
     * @param review
     */
    public static void isReviewStatusDelete(Review review){
        if(!review.isDeleted()){
            throw new ReviewStatusMismatchException("이 Review는 Delete 상태가 아닙니다." + review.getReviewStatus());
        }
    }
    /**
     * review의 status필드가 pending인지 확인합니다
     * @param review
     */
    public static void isReviewStatusPending(Review review){
        if(!review.isPending()){
            throw new ReviewStatusMismatchException("이 Review는 Pending 상태가 아닙니다." + review.getReviewStatus());
        }
    }

    public static void validatePageable(Pageable pageable){

        if(pageable == null) {
            throw new IllegalPageRequestException("pageable이 null입니다.");
        }

        if(pageable.getPageNumber() < 0){
            throw new IllegalPageRequestException("현재 페이지는 0 이상이어야 합니다.");
        }

        if(pageable.getOffset() < 0){
            throw new IllegalPageRequestException("조회 위치는 0 이상이어야 합니다.");
        }
    }



}
