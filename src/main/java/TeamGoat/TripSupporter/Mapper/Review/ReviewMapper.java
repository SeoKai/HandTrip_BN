package TeamGoat.TripSupporter.Mapper.Review;

import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.Review.Review;
import TeamGoat.TripSupporter.Domain.Entity.Review.ReviewImage;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Exception.Review.LocationNotFoundException;
import TeamGoat.TripSupporter.Exception.Review.UserNotFoundException;
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
     * review엔티티를 넣으면 reviewDto를 반환
     * @param review review엔티티
     * @return reviewDto
     */
    public ReviewDto ReviewConvertToDto(Review review) {
        return new ReviewDto(
                review.getReviewId(),
                review.getUser().getUserId(),           // user객체로부터 userid가져옴
                review.getLocation().getLocationId(),   // planner객체로부터 plannerid 가져옴
                review.getRating(),
                review.getComment(),
                review.getReviewCreatedAt(),
                review.getReviewUpdatedAt(),
                review.getReviewStatus(),
                review.getImages().stream() // 이미지 URL 리스트로 변환
                        .map(ReviewImage::getImageUrl)
                        .toList()
        );
    }

    /**
     * reviewDto를 넣으면 review 엔티티를 반환 (단 Review 엔티티에는 user,location 객체가 포함됨)
     * @param reviewDto reviewDto
     * @return review엔티티
     */
    public Review ReviewDtoConvertToEntity(ReviewDto reviewDto) {
        // User저장소에서 id로 user객체를 찾음
        User user = userRepository
                .findById(reviewDto.getUserId())
                .orElseThrow(()-> new UserNotFoundException("해당 Id에 일치하는 User가 존재하지 않습니다."+reviewDto.getUserId())
                );
        // Location저장소에서 id로 location객체를 찾음
        Location location = locationRepository
                .findById(reviewDto.getLocationId())
                .orElseThrow(()->new LocationNotFoundException("해당 Id에 일치하는 Location이 존재하지 않습니다."+reviewDto.getLocationId())
                );
        Review review =  new Review(
                reviewDto.getReviewId(),
                user,
                location,
                reviewDto.getRating(),
                reviewDto.getComment(),
                reviewDto.getReviewCreatedAt(),
                reviewDto.getReviewUpdatedAt(),
                reviewDto.getReviewStatus()
        );

        // ReviewDto의 이미지 URL 목록을 Review 엔티티에 추가
        if (reviewDto.getImageUrls() != null) {
            reviewDto.getImageUrls().forEach(review::addImage);
        }

        return review;
    }
}
