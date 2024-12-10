package TeamGoat.TripSupporter.Service;

import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewDto;
import TeamGoat.TripSupporter.Domain.Entity.Review.Review;
import TeamGoat.TripSupporter.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl{

    private final ReviewRepository reviewRepository;

    public List<ReviewDto> getReviewsByPlaceId(Long placeId) {
        return List.of();
    }

    public ReviewDto createReview(ReviewDto reviewDto) {
        return null;
    }

    public List<ReviewDto> getSortedReviews(Long placeId, String sortType) {
        return List.of();
    }

    /**
     * @param page : 시작 페이지 0부터 시작
     * @param size : 한 페이지당 항목 수
     * @return
     */
    public Page<Review> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findAllByOrderByReviewCreatedAtDesc(pageable);
    }
}
