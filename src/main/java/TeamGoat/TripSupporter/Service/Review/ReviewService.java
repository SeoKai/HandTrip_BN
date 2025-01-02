
package TeamGoat.TripSupporter.Service.Review;

import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewDto;
import org.springframework.data.domain.Page;

public interface ReviewService {

    /**
     * 특정 여행지의 리뷰를 가져옵니다.
     *
     * @param placeId 여행지 ID
     * @return 리뷰 목록
     */
    Page<ReviewDto> getReviewsByPlaceId(Long placeId);

    /**
     * 리뷰를 작성합니다.
     *
     * @param reviewDto 작성할 리뷰 DTO
     * @return 저장된 리뷰 DTO
     */
    ReviewDto createReview(ReviewDto reviewDto);

    /**
     * 작성된 리뷰를 정렬된 형식으로 가져옵니다.
     *
     * @param placeId 여행지 ID
     * @param sortType 정렬 기준 (예: 최신순, 별점순, 좋아요순)
     * @return 정렬된 리뷰 목록
     */
    Page<ReviewDto> getSortedReviews(Long placeId, String sortType);
}

