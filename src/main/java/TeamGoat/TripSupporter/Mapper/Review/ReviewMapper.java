package TeamGoat.TripSupporter.Mapper.Review;

import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewDto;
import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewWithUserProfileDto;
import TeamGoat.TripSupporter.Domain.Dto.User.UserProfileDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.Review.Review;
import TeamGoat.TripSupporter.Domain.Entity.Review.ReviewImage;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Exception.Location.LocationNotFoundException;
import TeamGoat.TripSupporter.Exception.UserNotFoundException;
import TeamGoat.TripSupporter.Repository.Location.LocationRepository;
import TeamGoat.TripSupporter.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    /**
     * Review 엔티티를 ReviewDto로 변환
     * @param review Review 엔티티
     * @return ReviewDto
     */
    public ReviewDto ReviewConvertToDto(Review review) {
        return ReviewDto.builder()
                .reviewId(review.getReviewId())
                .userEmail(review.getUser().getUserEmail()) // User 엔티티에서 User Email 추출
                .locationId(review.getLocation().getLocationId()) // Location 엔티티에서 Location ID 추출
                .title(review.getTitle())
                .rating(review.getRating())
                .comment(review.getComment())
                .reviewCreatedAt(review.getReviewCreatedAt())
                .reviewUpdatedAt(review.getReviewUpdatedAt())
                .reviewStatus(review.getReviewStatus())
                .imageUrls(review.getImageUrls().stream() // ReviewImage 리스트를 URL 리스트로 변환
                        .map(ReviewImage::getImageUrl)
                        .toList())
                .build();
    }

    /**
     * ReviewDto를 Review 엔티티로 변환 (User와 Location 객체 포함)
     * @param reviewDto ReviewDto
     * @return Review 엔티티
     */
    public Review ReviewDtoConvertToEntity(ReviewDto reviewDto) {
        // UserRepository에서 ID로 User 객체 조회
        User user = userRepository.findByUserEmail(reviewDto.getUserEmail())
                .orElseThrow(() -> new UserNotFoundException("해당 ID에 일치하는 User가 존재하지 않습니다: " + reviewDto.getUserEmail()));

        // LocationRepository에서 ID로 Location 객체 조회
        Location location = locationRepository.findById(reviewDto.getLocationId())
                .orElseThrow(() -> new LocationNotFoundException("해당 ID에 일치하는 Location이 존재하지 않습니다: " + reviewDto.getLocationId()));

        // Review 빌더를 사용하여 엔티티 생성
        Review review = Review.builder()
                .reviewId(reviewDto.getReviewId())
                .user(user) // User 객체 설정
                .location(location) // Location 객체 설정
                .title(reviewDto.getTitle())
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .reviewCreatedAt(reviewDto.getReviewCreatedAt())
                .reviewUpdatedAt(reviewDto.getReviewUpdatedAt())
                .reviewStatus(reviewDto.getReviewStatus())
                .build();

        // ReviewDto의 이미지 URL 목록을 Review 엔티티에 추가
        if (reviewDto.getImageUrls() != null) {
            reviewDto.getImageUrls().forEach(review::addImage);
        }

        return review;
    }


    // 새 메서드: Review를 ReviewWithUserProfileDto로 변환
    public ReviewWithUserProfileDto ReviewConvertToWithUserProfileDto(Review review) {
        UserProfileDto userProfileDto = UserProfileDto.builder()
                .userNickname(review.getUser().getUserProfile().getUserNickname())
                .profileImageUrl(review.getUser().getUserProfile().getProfileImageUrl())
                .userBio(review.getUser().getUserProfile().getUserBio())
                .build();

        ReviewDto reviewDto = ReviewConvertToDto(review);

        return ReviewWithUserProfileDto.builder()
                .reviewDto(reviewDto)
                .userProfileDto(userProfileDto)
                .build();
    }
}
