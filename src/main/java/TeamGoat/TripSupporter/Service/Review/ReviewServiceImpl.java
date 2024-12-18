package TeamGoat.TripSupporter.Service.Review;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewDto;
import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewWithLocationDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.Review.Review;
import TeamGoat.TripSupporter.Domain.Entity.Review.ReviewImage;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Domain.Enum.ReviewStatus;
import TeamGoat.TripSupporter.Exception.Review.LocationNotFoundException;
import TeamGoat.TripSupporter.Exception.Review.ReviewException;
import TeamGoat.TripSupporter.Exception.Review.ReviewNotFoundException;
import TeamGoat.TripSupporter.Exception.Review.UserNotFoundException;
import TeamGoat.TripSupporter.Repository.ReviewRepository;
import TeamGoat.TripSupporter.Repository.UserRepository;
import TeamGoat.TripSupporter.Service.Review.Util.ReviewServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl{

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    /**
     * review엔티티를 넣으면 reviewDto를 반환
     * @param review review엔티티
     * @return reviewDto
     */
    private ReviewDto ReviewConvertToDto(Review review) {
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
     * location엔티티를 넣으면 locationDto를 반환
     * @param location location엔티티
     * @return locationDto
     */
    private LocationDto LocationConvertToDto(Location location){
        return new LocationDto(
                location.getLocationId(),
                location.getPlaceId(),
                location.getLocationName(),
                location.getDescription(),
                location.getLatitude(),
                location.getLongitude(),
                location.getAddress(),
                location.getGoogleRating(),
                location.getTypes(),
                location.getPlaceImgUrl()
        );
    }

    /**
     * reviewDto를 넣으면 review 엔티티를 반환 (단 Review 엔티티에는 user,location 객체가 포함됨)
     * @param reviewDto reviewDto
     * @return review엔티티
     */
    private Review convertToEntity(ReviewDto reviewDto) {
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


    /**
     * 정렬기준에 따라 Pageable 객체 반환 (기본 정렬: ReviewCreatedAt DESC)
     * @param page : 시작 페이지 0부터 시작
     * @param sortValue : 정렬 기준 (ReviewCreatedAt, Rating)
     * @param sortDirection : 정렬 방향 (asc, desc)
     * @return : Pageable 객체
     */
    private Pageable getPageable(int page, String sortValue, String sortDirection) {
        // 정렬기준, 정렬 방향을 설정하지 않았을 경우 default 정렬 기준 : 최신순
        if (sortValue == null || sortValue.isEmpty()) {
            sortValue = "reviewCreatedAt";  // 기본 정렬 기준: ReviewCreatedAt
        }
        if (sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "desc";  // 기본 정렬 방향: 내림차순
        }

        // 정렬 기준을 sortValue로 설정
        Sort sort = Sort.by(sortValue);
        // 정렬 방향 확인
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        log.info("현재 Page : " + page + ",정렬 기준 : " + sortValue + ",정렬 방향 : " +sortDirection);

        return PageRequest.of(page, 5, sort);
    }

    /**
     * 모든 활성화 상태의 데이터 검색
     * @param page : 시작 페이지 0부터 시작
     * @param sortValue : 정렬 기준 (ReviewCreatedAt, Rating), null 또는 비어있으면 ReviewCreatedAt기준
     * @param sortDirection : 정렬 방향 (asc, desc), null또는 비어있으면 내림차순
     * @return : 페이징처리된 리뷰 Dto
     */
    public Page<ReviewDto> getReviews(int page, String sortValue, String sortDirection) {
        // 입력받은 값들로 pageable 객체 생성
        Pageable pageable = getPageable(page,sortValue,sortDirection);
        // 생성된 pageable 객체를 Repository에 전달
        Page<Review> reviews = reviewRepository.findByReviewStatus(ReviewStatus.ACTIVE,pageable);

        log.info("모든 리뷰를 "+sortValue+" 기준으로" + sortDirection + " 방향으로 정렬합니다.");
        // 페이징 처리된 review를 Dto로 변환하여 반환
        return reviews.map(this::ReviewConvertToDto);
    }

    /**
     * 활성화 상태 리뷰중에서 여행지 기준 검색
     * @param locationId : 여행지아이디
     * @param page : 시작 페이지 0부터 시작
     * @param sortValue : 정렬 기준 (ReviewCreatedAt, Rating), null 또는 비어있으면 ReviewCreatedAt기준
     * @param sortDirection : 정렬 방향 (asc, desc), null또는 비어있으면 내림차순
     * @return : 페이징처리된 리뷰 Dto
     */
    public Page<ReviewDto> getReviewsByLocationId(Long locationId, int page, String sortValue, String sortDirection) {
        // 입력받은 값들로 pageable 객체 생성
        Pageable pageable = getPageable(page,sortValue,sortDirection);
        // locationId 유효성검사
        ReviewServiceValidator.validateLocationId(locationId);
        // 생성된 pageable 객체와 입력받은 locationId를 Repository에 전달
        Page<Review> reviews = reviewRepository.findByLocation_LocationIdAndReviewStatus(locationId,ReviewStatus.ACTIVE,pageable);

        log.info("Location에서 " + locationId + "로 검색한 결과를 "+sortValue+"기준으로 " + sortDirection + "방향으로 정렬합니다.");
        // 페이징 처리된 review를 Dto로 변환하여 반환
        return reviews.map(this::ReviewConvertToDto);
    }

    /**
     * 활성화 상태 리뷰중에서 작성자 기준 검색
     * @param userId : 사용자아이디
     * @param page : 시작 페이지 0부터 시작
     * @param sortValue : 정렬 기준 (ReviewCreatedAt, Rating), null 또는 비어있으면 ReviewCreatedAt기준
     * @param sortDirection : 정렬 방향 (asc, desc), null또는 비어있으면 내림차순
     * @return : 페이징처리된 리뷰 Dto
     */
    public Page<ReviewDto> getReviewsByUserId(Long userId, int page, String sortValue, String sortDirection) {
        // 입력받은 값들로 pageable 객체 생성
        Pageable pageable = getPageable(page,sortValue,sortDirection);
        // userId 유효성검사
        ReviewServiceValidator.validateUserId(userId);
        // 생성된 pageable 객체와 입력받은 userId를 Repository에 전달
        Page<Review> reviews = reviewRepository.findByUser_UserIdAndReviewStatus(userId,ReviewStatus.ACTIVE,pageable);

        log.info("User에서 " + userId + "로 검색한 결과를 " + sortValue + " 기준으로" + sortDirection + " 방향으로 정렬합니다.");
        // 페이징 처리된 review를 Dto로 변환하여 반환
        return reviews.map(this::ReviewConvertToDto);
    }

    /**
     * reviewId로 리뷰 하나 찾는 메서드
     * @param reviewId
     * @return  ReviewDto로 반환함
     */
    public ReviewDto getReviewById(Long reviewId) {
        // reviewId로 review 가져옴
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾을 수 없습니다."));
        // 가져온 review의 상태가 유효한지 검사
        ReviewServiceValidator.isReviewStatusActive(review);
        // 가져온 리뷰를 dto로 변환해서 반환
        return ReviewConvertToDto(review);
    }

    /**
     * ReviewId로 Review와 Location을 찾아 함께 ReviewWithLocationDto로 반환합니다.
     * 입력받은 사용자정보의 userId와 review의 작성자인 userId가 다르다면 예외를 던집니다.
     * @param userId
     * @param reviewId
     * @return ReviewWithLocationDto는 reviewDto와 locationDto를 결합한 Dto
     */
    public ReviewWithLocationDto getReviewWithLocationById(Long userId, Long reviewId) {
        // reviewId로 review 가져옴
        Review review = reviewRepository.findByIdAndReviewStatus(reviewId, ReviewStatus.ACTIVE)
                .orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾을 수 없습니다."));
        // 가져온 review로부터 userId를 추출하여 입력받은 userId와 같은지 확인함
        ReviewServiceValidator.validateUserAndAuthor(review.getUser().getUserId(),userId);
        // 가져온 review의 상태가 유효한지 검사
        ReviewServiceValidator.isReviewStatusActive(review);
        // Review로부터 location 가져옴
        Location location = review.getLocation();
        // 잘 가져왔는지 확인
        ReviewServiceValidator.validateLocationId(location.getLocationId());
        // 가져온 review와 location를 dto로 변환
        ReviewDto reviewDto = ReviewConvertToDto(review);
        LocationDto locationDto = LocationConvertToDto(location);

        // 가져온 리뷰를 dto로 변환해서 반환
        return new ReviewWithLocationDto(reviewDto, locationDto);
    }

    /**
     * 작성된 리뷰를 저장함
     * @param reviewDto 작성된 리뷰 데이터 객체
     */
    public void createReview(ReviewDto reviewDto){
        try {
            // dto를 review객체로 변환
            Review review = convertToEntity(reviewDto);

            // 외래키 제대로 불러왔는지 확인
            ReviewServiceValidator.validateUserId(review.getUser().getUserId());
            ReviewServiceValidator.validateLocationId(review.getLocation().getLocationId());

            // 이미지 추가
            if (reviewDto.getImageUrls() != null) {
                reviewDto.getImageUrls().forEach(review::addImage);
            }

            // 검사가 끝난 후 저장
            reviewRepository.save(review);
            log.info("review를 성공적으로 저장하였습니다. 저장된 리뷰 아이디 : "+review.getReviewId());

        }catch (Exception e) {
            // 데이터베이스 관련 예외
            ReviewException("Review 생성중 오류 발생",e);
        }
    }

    /**
     * 이미 작성된 리뷰를 불러와서 수정함
     * @param reviewDto 수정된 리뷰 데이터 객체
     */
    public void updateReview(Long userId , ReviewDto reviewDto) {

        try{
            // 우선 ReviewDto의 id로 수정되기전 review를 가져온다.
            Review existingReview = reviewRepository.findById(reviewDto.getReviewId())
                    .orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾을 수 없습니다."));

            // 수정되기전 review의 userId와, 수정된 review data의 userId가 일치하는지 확인한다.
            ReviewServiceValidator.validateUserAndAuthor(existingReview,reviewDto);

            // 기존 이미지 목록 제거 후 DTO에서 새 이미지 추가
            existingReview.getImages().clear();  // 기존 이미지 삭제

            // 새로운 이미지 추가
            if (reviewDto.getImageUrls() != null) {
                reviewDto.getImageUrls().forEach(existingReview::addImage);
            }

            // entity에서 update 메서드 실행 (Rating과 Comment 수정, UpdateAt 갱신)
            existingReview.updateReview(reviewDto.getRating(), reviewDto.getComment());
            reviewRepository.save(existingReview);

            log.info("review를 성공적으로 수정하였습니다. " +
                    "\n수정된 ReviewId : "+existingReview.getReviewId()+
                    "\n수정된 Rating : " + existingReview.getRating()+
                    "\n수정된 Comment : " + existingReview.getComment());

        }catch (Exception e) {
            // 데이터베이스 관련 예외
            ReviewException("Review 수정중 오류 발생",e);
        }

    }

    /**
     * 삭제할 리뷰 id와 삭제하는 사용자의 id를 받아
     * 사용자 id와 작성자 id가 일치하는지 확인한 후 삭제
     * @param userId    삭제하는 사용자 id
     * @param reviewId  삭제할 리뷰 id
     */

    public void deleteReview(Long userId,Long reviewId) {
        try{
            Review existingReview = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾을 수 없습니다."));

            // 작성자와 user가 일치하는지 확인
            ReviewServiceValidator.validateUserAndAuthor(existingReview,userId);

            reviewRepository.delete(existingReview);
            log.info(existingReview.getReviewId()+"의 삭제가 완료되었습니다.");

        }catch (Exception e) {
            // 데이터베이스 관련 예외
            ReviewException("Review 삭제중 오류 발생",e);
        }
    }

    public double calculateAverageRating(Long locationId) {

        try{
            ReviewServiceValidator.validateLocationId(locationId);
            // Repository로부터 locationId를 가진 Review List가 담긴 Optional객체를 받아온다
            Optional<List<Review>> locationReviews = reviewRepository.findByLocation_LocationId(locationId);
            // Optional객체의 값이 존재하는지 확인
            if (locationReviews.isPresent()) {
                log.info("locationId로 List<Review>를 불러오는대 성공하였습니다.");
                List<Review> reviewList = locationReviews.get();
                return reviewList.stream()
                        .mapToInt(Review::getRating)
                        .average()
                        .orElse(0.0);
            } else {
                return 0.0; // 리뷰가 존재하지 않는 경우
            }
        }catch(Exception e){
            ReviewException("rating 평균 계산중 오류 발생",e);
            throw e;
        }
    }

    /**
     * 모든 CRUD메서드에 중복된 예외처리 구문을 추출하여 관리하는 메서드
     * DataAccessException, IllegalArgumentException, ConstraintViolationException를 던짐
     * Review를 찾지 못할 경우 발생하는 ReviewNotFoundException과
     * 그외에 ReviewService에서 발생하는 나머지 예외인 ReviewException를 던짐
     * @param message   예외발생 시 던질 메시지
     * @param e 상세 예외클래스
     */
    private void ReviewException(String message,Exception e) {
        log.error("예외 발생" + message + " - 발생한 예외: " + e.getClass().getSimpleName(), e);
        if (e instanceof DataAccessException) {
            throw new ReviewException(message + " - 데이터베이스 오류가 발생했습니다", e);
        } else if (e instanceof IllegalArgumentException) {
            throw new ReviewException(message + " - 잘못된 데이터가 제공되었습니다", e);
        } else if (e instanceof ConstraintViolationException) {
            throw new ReviewException(message + " - 유효하지 않은 값이 전달되었습니다", e);
        } else if (e instanceof ReviewNotFoundException) {
            throw (ReviewNotFoundException) e;
        } else {
            throw new ReviewException(message + " - 예상치 못한 오류가 발생했습니다", e);
        }
    }

}

