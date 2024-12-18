package TeamGoat.TripSupporter.Controller.Review;

import TeamGoat.TripSupporter.Controller.Review.Util.ReviewContollerValidator;
import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewDto;
import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewWithLocationDto;
import TeamGoat.TripSupporter.Mapper.Review.*;
import TeamGoat.TripSupporter.Service.Review.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Slf4j
public class ReviewController {

    private final ReviewServiceImpl reviewService;
    private final PagedResourcesAssembler<ReviewDto> pagedResourcesAssembler;

    /**
     * 리뷰 생성
     * @param userId 로그인된 사용자 ID (유효성 검사를 통해 인증된 사용자임을 확인)
     * @param reviewDto 리뷰 데이터 (평점, 댓글 등 필수 입력값 포함)
     * @return 성공 메시지 (리뷰가 성공적으로 저장되었음을 나타냄)
     * @throws ReviewDtoNullException 리뷰 데이터가 null인 경우
     * @throws ReviewRatingNullException 평점이 null이거나 유효하지 않은 경우
     * @throws ReviewCommentNullException 댓글이 null이거나 길이 제한을 초과한 경우
     */
    //이거 locationId null로 들어오는지 강사님한테 함 물어보기
    @PostMapping("/create")
    public ResponseEntity<String> createReview(@RequestHeader ("userId") Long userId,@RequestBody ReviewDto reviewDto) {

        // 로그인한 사용자와 review를 쓴 사람이 같아야함
        ReviewContollerValidator.compareLongTypeNumber(userId, reviewDto.getUserId());
        // Dto와 필수입력 파라미터 (rating, comment)이 null인지 확인함
        ReviewContollerValidator.validateUserId(userId);
        ReviewContollerValidator.validateReviewDto(reviewDto);
        // rating이 유효한 값인지 확인함 (1~5 사이)
        ReviewContollerValidator.validateRating(reviewDto.getRating());
        // comment가 유효한 값인지 확인함 (1~500글자 사이)
        ReviewContollerValidator.validateComment(reviewDto.getComment());
        // 유효성 검사 완료 후 서비스로 넘김
        reviewService.createReview(reviewDto);
        // 서비스에서 처리 후 처리 결과 반환
        return ResponseEntity.ok("리뷰를 성공적으로 작성하였습니다.");
    }


    /**
     * 리뷰 수정 페이지를 보여주기 위한 작성된 리뷰 데이터를 가져오는 메서드
     * @param userId 로그인된 사용자 ID
     * @param reviewId 수정할 리뷰의 ID
     * @return reviewId에 해당하는 리뷰와 해당 리뷰에 연결된 위치 정보를 포함한 ReviewWithLocationDto 객체를 반환
     */
    @GetMapping("/{reviewId}/edit")
    public ResponseEntity<ReviewWithLocationDto> showReviewUpdatePage(@RequestHeader ("userId") Long userId,@PathVariable(name = "reviewId") Long reviewId) {
        // 파라미터 null체크
        ReviewContollerValidator.validateUserId(userId);
        ReviewContollerValidator.validateReviewId(reviewId);
        // reviewId로 reviewDto 가져옴
        ReviewWithLocationDto ReviewWithLocationDto = reviewService.getReviewWithLocationById(userId,reviewId);
        // service에서 받은 ReviewWithLocationDto 객체 반환
        return ResponseEntity.ok(ReviewWithLocationDto);
    }
//    추후 아래 이슈 해결하기
//    다만, userId가 인증된 사용자 정보라면, 헤더나 세션에서 사용자 정보를 가져오는 방식이 더 일반적입니다.
//    현재 API에서 userId를 명시적으로 전달받는 것이 비즈니스 로직에서 필수라면, 이를 그대로 유지해도 괜찮습니다.


    /**
     * 리뷰 수정
     * @param userId 로그인된 사용자 ID
     * @param reviewId 리뷰 ID
     * @param reviewDto 수정할 리뷰 데이터
     * @return 성공 메시지
     */
    @PutMapping("/{reviewId}/edit/completed")
    public ResponseEntity<String> reviewUpdate( @RequestHeader ("userId") Long userId, @PathVariable(name = "reviewId") Long reviewId , @RequestBody ReviewDto reviewDto) {

        // Id값들 유효성 확인
        ReviewContollerValidator.validateReviewId(reviewId);
        ReviewContollerValidator.validateUserId(userId);
        // 제출하는 수정리뷰(reviewDto) reviewId랑 수정하는 게시물의 reviewId가 같아야함
        ReviewContollerValidator.compareLongTypeNumber(reviewId,reviewDto.getReviewId());
        // 현재 수정중인 로그인 상태의 사용자 userId와 제출하는 수정리뷰(reviewDto)를 작성한 userId가 같아야함
        ReviewContollerValidator.compareLongTypeNumber(userId,reviewDto.getUserId());

        // Dto와 필수입력 파라미터 (rating, comment)이 null인지 확인함
        ReviewContollerValidator.validateReviewDto(reviewDto);
        // rating이 유효한 값인지 확인함 (1~5 사이)
        ReviewContollerValidator.validateRating(reviewDto.getRating());
        // comment가 유효한 값인지 확인함 (1~500글자 사이)
        ReviewContollerValidator.validateComment(reviewDto.getComment());
        // 유효성 검사 완료 후 서비스로 넘김
        reviewService.updateReview(userId,reviewDto);
        // 서비스에서 처리 후 처리 결과 반환
        return ResponseEntity.ok("리뷰 수정 성공");
    }

    /**
     * 리뷰 삭제
     * @param userId 로그인된 사용자 ID (삭제 권한 확인에 사용)
     * @param reviewId 삭제할 리뷰 ID
     * @return 성공 메시지 (리뷰가 삭제되었음을 나타냄)
     * @throws ReviewNotFoundException 리뷰가 존재하지 않을 경우
     * @throws ReviewAuthorMismatchException 리뷰 작성자와 삭제 요청 사용자가 다를 경우
     */
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@RequestHeader ("userId") Long userId, @PathVariable(name = "reviewId") Long reviewId) {
        //파라미터 null체크
        ReviewContollerValidator.validateReviewId(reviewId);
        ReviewContollerValidator.validateUserId(userId);
        // 유효성 검사 완료 후 서비스로 넘김
        reviewService.deleteReview(userId , reviewId);
        // 서비스에서 처리 후 처리 결과 반환
        return ResponseEntity.ok("리뷰 삭제 성공");
    }
//    추후 아래 이슈 해결하기
//    다만, userId가 인증된 사용자 정보라면, 헤더나 세션에서 사용자 정보를 가져오는 방식이 더 일반적입니다.
//    현재 API에서 userId를 명시적으로 전달받는 것이 비즈니스 로직에서 필수라면, 이를 그대로 유지해도 괜찮습니다.

    /**
     * 리뷰 조회
     * @param page 페이지 번호 (0부터 시작, 기본값: 0)
     * @param sortValue 정렬 기준 (reviewCreatedAt, rating 중 하나, 기본값: reviewCreatedAt)
     * @param sortDirection 정렬 방향 (asc 또는 desc, 기본값: desc)
     * @param locationId 여행지 ID (옵션, 특정 여행지의 리뷰를 조회할 때 사용)
     * @param userId 사용자 ID (옵션, 특정 사용자가 작성한 리뷰를 조회할 때 사용)
     * @return 페이징된 리뷰 목록
     * @throws IllegalArgumentException 잘못된 정렬 기준 또는 방향이 전달된 경우
     */
    @GetMapping("/getPagedReviews")
    public ResponseEntity<PagedModel<EntityModel<ReviewDto>>> getReviews(
            @RequestParam(name = "page" , defaultValue = "0") Integer page,
            @RequestParam(name = "sortValue" , defaultValue = "reviewCreatedAt") String sortValue,
            @RequestParam(name = "sortDirection" , defaultValue = "desc") String sortDirection,
            @RequestParam(name = "locationId" , required = false) Long locationId,
            @RequestParam(name = "userId" , required = false) Long userId) {

        // 외래키 id값들 null 체크
        if(locationId != null)
            ReviewContollerValidator.validateLocationId(locationId); // 여행지 ID 유효성 검사
        if(userId != null)
            ReviewContollerValidator.validateUserId(userId); // 사용자 ID 유효성 검사

        // 페이지 번호, 정렬 기준, 정렬 방향 유효성 검사
//        int pageNumber = Integer.parseInt(page);
        ReviewContollerValidator.validatePageNumber(page);
        log.info(String.valueOf(page));
        log.info(page.getClass().getName());
        ReviewContollerValidator.validateSortValue(sortValue);
        ReviewContollerValidator.validateSortDirection(sortDirection);

        Page<ReviewDto> reviews;

        if (locationId != null) {
            // 특정 여행지 리뷰 조회
            reviews = reviewService.getReviewsByLocationId(locationId, page, sortValue, sortDirection);
        } else if (userId != null) {
            // 특정 사용자 리뷰 조회
            reviews = reviewService.getReviewsByUserId(userId, page, sortValue, sortDirection);
        } else {
            // 모든 리뷰 조회
            reviews = reviewService.getReviews(page, sortValue, sortDirection);
        }
        // Page<ReviewDto> 를 PagedModel<EntityModel<ReviewDto>>로 변환
        log.info("page : {}",page);
        PagedModel<EntityModel<ReviewDto>> pagedModel = pagedResourcesAssembler.toModel(reviews);
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * 리뷰 평점 평균 계산
     * @param locationId 여행지 ID
     * @return 평균 평점
     */
    @GetMapping("/average/{locationId}")
    public ResponseEntity<Double> calculateAverageRating(@RequestParam Long locationId) {
        // 파라미터 유효성 체크
        ReviewContollerValidator.validateLocationId(locationId);
        // 유효성 검사 완료 후 서비스로 넘김
        double averageRating = reviewService.calculateAverageRating(locationId);
        // 계산된 해당지역에 대한 리뷰 평균을 반환함
        return ResponseEntity.ok(averageRating);
    }
}

