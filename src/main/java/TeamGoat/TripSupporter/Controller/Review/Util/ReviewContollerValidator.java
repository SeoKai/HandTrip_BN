package TeamGoat.TripSupporter.Controller.Review.Util;

import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewDto;
import TeamGoat.TripSupporter.Mapper.Review.*;

public class ReviewContollerValidator {

    /**
     * ReviewDto에서 입력필수 항목이 null인지 확인함.
     * 현재 입력필수항목은 comment, rating
     * @param reviewDto
     */
    public static void validateReviewDto(ReviewDto reviewDto) {
        if (reviewDto == null) {
            throw new ReviewDtoNullException("필수 입력란을 확인해주세요");
        }
        if (reviewDto.getComment() == null || reviewDto.getComment().isEmpty()) {
            throw new ReviewCommentNullException("본문은 1자 이상 작성해야합니다. 다시 확인해주세요.");
        }
        if (reviewDto.getRating() == null) {
            throw new ReviewRatingNullException("평점은 0점을 줄 수 없습니다. 다시 확인해주세요.");
        }
    }

    /**
     * reviewId null 체크
     * @param reviewId
     */
    public static void validateReviewId(Long reviewId) {
        if (reviewId == null) {
            throw new ReviewNotFoundException("리뷰를 찾을 수 없습니다.");
        }
    }

    /**
     * userid null 체크
     * @param userId
     */
    public static void validateUserId(Long userId) {
        if (userId == null) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }

    /**
     * loacationId null 체크
     * @param locationId
     */
    public static void validateLocationId(Long locationId) {
        if (locationId == null) {
            throw new LocationNotFoundException("해당 여행지를 찾을 수 없습니다.");
        }
    }

    /**
     * rating값의 범위가 올바른지 검사함
     * rating은 1~5사이값이어야함
     * @param rating 별점
     */
    public static void validateRating(Integer rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1에서 5 사이의 값이어야 합니다.");
        }
    }

    /**
     * 본문 내용의 글자수를 검사함
     * 현재 1자이상 500자이하까지 가능
     * @param comment 본문내용
     */
    public static void validateComment(String comment) {
        if (comment != null && (comment.length() < 1 || comment.length() > 500)) {
            throw new IllegalArgumentException("댓글은 1자 이상 500자 이하로 작성해야 합니다.");
        }
    }

    /**
     * 정렬기준이 유효한지 검사함
     * 현재 CreateAt과 Rating기준 정렬 지원
     * @param sortValue 정렬기준
     */
    public static void validateSortValue(String sortValue) {
        if (!"reviewCreatedAt".equals(sortValue) && !"rating".equals(sortValue)) {
            throw new IllegalArgumentException("잘못된 정렬 기준입니다.");
        }
    }

    /**
     * 정렬 방향이 유효한지 검사함 asc,desc
     * @param sortDirection 정렬방향
     */
    public static void validateSortDirection(String sortDirection) {
        if (!"asc".equals(sortDirection) && !"desc".equals(sortDirection)) {
            throw new IllegalArgumentException("잘못된 정렬 방향입니다.");
        }
    }

    /**
     * Page가 유효한지 검사함
     * Page는 반드시 0보다 커야함
     * @param page
     */
    public static void validatePageNumber(int page) {
        if (page < 0) {
            throw new IllegalArgumentException("페이지 번호는 0 이상이어야 합니다.");
        }
    }

    /**
     * 두수를 입력받고 두수가 다르다면 예외를 던집니다.
     * @param id1
     * @param id2
     */
    public static void compareLongTypeNumber(Long id1, Long id2) {
        if (!id1.equals(id2)) {
            throw new MismatchedIdsException("입력된 두 값이 다릅니다: " + id1 + "와 " + id2);
        }
    }

}
