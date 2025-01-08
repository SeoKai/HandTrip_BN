package TeamGoat.TripSupporter.Controller.Review;

import TeamGoat.TripSupporter.Config.auth.JwtTokenProvider;
import TeamGoat.TripSupporter.Controller.Review.Util.ReviewContollerValidator;
import TeamGoat.TripSupporter.Domain.Dto.Ai.AiUserDto;
import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewDto;
import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewWithLocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewWithUserProfileDto;
import TeamGoat.TripSupporter.Exception.Review.*;
import TeamGoat.TripSupporter.Service.Ai.AiRecommendationService;
import TeamGoat.TripSupporter.Service.Review.ReviewServiceImpl;
import TeamGoat.TripSupporter.Service.Review.Util.ReviewServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Slf4j
@Validated
public class ReviewController {

    private final ReviewServiceImpl reviewService;
    private final PagedResourcesAssembler<ReviewWithUserProfileDto> pagedResourcesAssemblerWithUser;
    private final PagedResourcesAssembler<ReviewWithLocationDto> pagedResourcesAssemblerWithLocation;
    private final JwtTokenProvider jwtTokenProvider;
    private final AiRecommendationService aiRecommendationService;

    @Value("${file.review-upload-dir}")
    private String uploadDir;

//    private final String uploadDir = "upload/images/review/";  // 업로드 디렉토리 경로 (절대 경로 또는 상대 경로로 설정 가능)


    /**
     * 리뷰 생성
     * @param authorization 로그인된 사용자 토큰 (유효성 검사를 통해 인증된 사용자임을 확인)
     * @param reviewDto 리뷰 데이터 (평점, 댓글 등 필수 입력값 포함)
     * @return 성공 메시지 (리뷰가 성공적으로 저장되었음을 나타냄)
     * @throws ReviewDtoNullException 리뷰 데이터가 null인 경우
     * @throws ReviewRatingNullException 평점이 null이거나 유효하지 않은 경우
     * @throws ReviewCommentNullException 댓글이 null이거나 길이 제한을 초과한 경우
     */
    @PostMapping("/create")
    public ResponseEntity<String> createReview(@RequestHeader("Authorization") String authorization,@RequestBody ReviewDto reviewDto) {

        log.info("reviewController createReview Method invoke 파라미터 확인 - token : {}, reviewDto : {}",authorization,reviewDto);
        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);

        // Dto와 필수입력 파라미터 (rating, comment)이 null인지 확인함
        ReviewContollerValidator.validateUserEmail(userEmail);
        ReviewContollerValidator.validateReviewDto(reviewDto);
        //reviewDto에 userEmail 정보 저장
        reviewDto.setUserEmail(userEmail);

        // rating이 유효한 값인지 확인함 (1~5 사이)
        ReviewContollerValidator.validateRating(reviewDto.getRating());
        // comment가 유효한 값인지 확인함 (1~500글자 사이)
        ReviewContollerValidator.validateComment(reviewDto.getComment());
        // 유효성 검사 완료 후 서비스로 넘김
        reviewService.createReview(reviewDto);

        // AI 데이터 저장
        Long userId = aiRecommendationService.getUserIdByEmail(userEmail);
        if (userId != null) {
            AiUserDto aiUserDto = AiUserDto.builder()
                    .userId(userId)
                    .locationId(reviewDto.getLocationId())
                    .rating(reviewDto.getRating())
                    .build();
            aiRecommendationService.saveRecommendations(List.of(aiUserDto));
        }
        else {
            log.warn("AI 데이터 저장 실패 : userId를 찾을 수 없습니다.");
        }

        // 서비스에서 처리 후 처리 결과 반환
        return ResponseEntity.ok("리뷰를 성공적으로 작성하였습니다.");
    }


    /**
     * 리뷰 수정 페이지를 보여주기 위한 작성된 리뷰 데이터를 가져오는 메서드
     * @param authorization 로그인된 사용자 토큰정보
     * @param reviewId 수정할 리뷰의 ID
     * @return reviewId에 해당하는 리뷰와 해당 리뷰에 연결된 위치 정보를 포함한 ReviewWithLocationDto 객체를 반환
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewWithLocationDto> showReviewUpdatePage(@RequestHeader("Authorization") String authorization,
                                                                      @PathVariable(name = "reviewId") Long reviewId) {

        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);
        // 파라미터 null체크
        ReviewContollerValidator.validateUserEmail(userEmail);
        ReviewContollerValidator.validateReviewId(reviewId);
        // reviewId로 reviewDto 가져옴
        ReviewWithLocationDto ReviewWithLocationDto = reviewService.getReviewWithLocationById(userEmail,reviewId);
        // service에서 받은 ReviewWithLocationDto 객체 반환
        return ResponseEntity.ok(ReviewWithLocationDto);
    }

    /**
     * 리뷰 수정
     * @param authorization 로그인된 사용자 토큰
     * @param reviewId 리뷰 ID
     * @param reviewDto 수정할 리뷰 데이터
     * @return 성공 메시지
     */
    @PutMapping("/{reviewId}/edit")
    public ResponseEntity<String> reviewUpdate( @RequestHeader("Authorization")String authorization ,
                                                @PathVariable(name = "reviewId") Long reviewId ,
                                                @RequestBody ReviewDto reviewDto) {

        log.info("reviewController reviewUpdate Method invoke 파라미터 확인 - token : {}, reviewId : {}, reviewDto : {}", authorization, reviewId, reviewDto);
        // token에서 userEmail 추출
        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);
        // Id값들 유효성 확인
        ReviewContollerValidator.validateReviewId(reviewId);
        ReviewContollerValidator.validateUserEmail(userEmail);
        // Dto와 필수입력 파라미터 (rating, comment)이 null인지 확인함
        ReviewContollerValidator.validateReviewDto(reviewDto);
        // 제출하는 수정리뷰(reviewDto) reviewId랑 수정하는 게시물의 reviewId가 같아야함
        ReviewContollerValidator.compareLongTypeNumber(reviewId,reviewDto.getReviewId());

        // rating이 유효한 값인지 확인함 (1~5 사이)
        ReviewContollerValidator.validateRating(reviewDto.getRating());
        // comment가 유효한 값인지 확인함 (1~500글자 사이)
        ReviewContollerValidator.validateComment(reviewDto.getComment());

        // 유효성 검사 완료 후 서비스로 넘김
        reviewService.updateReview(userEmail,reviewDto);
        // 서비스에서 처리 후 처리 결과 반환
        return ResponseEntity.ok("리뷰 수정 성공");
    }
    /**
     * 리뷰 삭제
     * @param authorization 로그인된 사용자 토큰 (삭제 권한 확인에 사용)
     * @param reviewId 삭제할 리뷰 ID
     * @return 성공 메시지 (리뷰가 삭제되었음을 나타냄)
     * @throws ReviewNotFoundException 리뷰가 존재하지 않을 경우
     * @throws ReviewAuthorMismatchException 리뷰 작성자와 삭제 요청 사용자가 다를 경우
     */
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview( @RequestHeader("Authorization")String authorization, @PathVariable(name = "reviewId") Long reviewId) {

        log.info("reviewController deleteReview Method invoke 파라미터 확인 - token : {}, reviewId : {}", authorization, reviewId);
        // token에서 userEmail 추출
        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);
        // null체크
        ReviewContollerValidator.validateReviewId(reviewId);
        ReviewContollerValidator.validateUserEmail(userEmail);
        // 유효성 검사 완료 후 서비스로 넘김
        reviewService.deleteReview(userEmail , reviewId);
        // 서비스에서 처리 후 처리 결과 반환
        return ResponseEntity.ok("리뷰 삭제 성공");
    }
//    추후 아래 이슈 해결하기
//    다만, userId가 인증된 사용자 정보라면, 헤더나 세션에서 사용자 정보를 가져오는 방식이 더 일반적입니다.
//    현재 API에서 userId를 명시적으로 전달받는 것이 비즈니스 로직에서 필수라면, 이를 그대로 유지해도 괜찮습니다.

    /**
     * 리뷰 조회 ( 사용자 정보 같이 반환 )
     * @param authorization 사용자 토큰 (옵션, 특정 사용자가 작성한 리뷰를 조회할 때 사용)
     * @param locationId 여행지 ID (옵션, 특정 여행지의 리뷰를 조회할 때 사용)
     * @param page 페이지 번호 (0부터 시작, 기본값: 0)
     * @param sortValue 정렬 기준 (reviewCreatedAt, rating 중 하나, 기본값: reviewCreatedAt)
     * @param sortDirection 정렬 방향 (asc 또는 desc, 기본값: desc)
     * @return 페이징된 리뷰 목록
     * @throws IllegalArgumentException 잘못된 정렬 기준 또는 방향이 전달된 경우
     */
    @GetMapping("/getReviewsWithUser")
    public ResponseEntity<PagedModel<EntityModel<ReviewWithUserProfileDto>>> getReviewsWithUser(
            @RequestHeader(value = "Authorization", required = false)String authorization,
            @RequestParam(name = "locationId" , required = false) Long locationId,
            @RequestParam(name = "page" , defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortValue" , defaultValue = "reviewCreatedAt") String sortValue,
            @RequestParam(name = "sortDirection" , defaultValue = "desc") String sortDirection
            ) {

        log.info("GET /getPagedReviews - locationId: {}, page: {},pageSize: {}, sortValue: {},sortDirection: {}",
                locationId, page,pageSize, sortValue, sortDirection);
        log.info("authorization : {} ",authorization);
        // token에서 userEmail 추출

        String userEmail = null;
        if (authorization != null && !authorization.isEmpty()) {
            String token = authorization.replace("Bearer ", "");
            userEmail = jwtTokenProvider.extractUserEmail(token);
            log.info("userEmail: {}", userEmail);
        }

        // 외래키 id값들 null 체크
        if(locationId != null)
            ReviewContollerValidator.validateLocationId(locationId); // 여행지 ID 유효성 검사
        if(userEmail != null)
            ReviewContollerValidator.validateUserEmail(userEmail); // 사용자 email 유효성 검사

        // 페이지 번호, 정렬 기준, 정렬 방향 유효성 검사
        ReviewContollerValidator.validatePageNumber(page);
        ReviewContollerValidator.validateSortValue(sortValue);
        ReviewContollerValidator.validateSortDirection(sortDirection);

        Page<ReviewWithUserProfileDto> reviews;

        if (locationId != null) {
            // 특정 여행지 리뷰 조회
            reviews = reviewService.getReviewsByLocationId(locationId, page, pageSize, sortValue, sortDirection);
        } else if (userEmail != null) {
            // 특정 사용자 리뷰 조회
            reviews = reviewService.getReviewsByUserId(userEmail, page, pageSize, sortValue, sortDirection);
        } else {
            // 모든 리뷰 조회
            reviews = reviewService.getReviews(page, pageSize, sortValue, sortDirection);
        }
        // Page<ReviewDto> 를 PagedModel<EntityModel<ReviewDto>>로 변환
        PagedModel<EntityModel<ReviewWithUserProfileDto>> pagedModel = pagedResourcesAssemblerWithUser.toModel(reviews);

        return ResponseEntity.ok(pagedModel);
    }


    /**
     * 리뷰 조회 ( Location정보 같이 반환 )
     * @param authorization 사용자 토큰 (옵션, 특정 사용자가 작성한 리뷰를 조회할 때 사용)
     * @param page 페이지 번호 (0부터 시작, 기본값: 0)
     * @param sortValue 정렬 기준 (reviewCreatedAt, rating 중 하나, 기본값: reviewCreatedAt)
     * @param sortDirection 정렬 방향 (asc 또는 desc, 기본값: desc)
     * @return 페이징된 리뷰 목록
     * @throws IllegalArgumentException 잘못된 정렬 기준 또는 방향이 전달된 경우
     */
    @GetMapping("/getReviewsWithLocation")
    public ResponseEntity<PagedModel<EntityModel<ReviewWithLocationDto>>> getReviewsWithLocation(
            @RequestHeader("Authorization")String authorization,
            @RequestParam(name = "page" , defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortValue" , defaultValue = "reviewCreatedAt") String sortValue,
            @RequestParam(name = "sortDirection" , defaultValue = "desc") String sortDirection
    ) {

        log.info("GET /getPagedReviews - page: {},pageSize: {}, sortValue: {},sortDirection: {}",
                page,pageSize, sortValue, sortDirection);
        log.info("authorization : {} ",authorization);
        // token에서 userEmail 추출
        String token = authorization.replace("Bearer ", "");
        String userEmail = jwtTokenProvider.extractUserEmail(token);

        log.info("userEmail : {} ",userEmail);
        // 외래키 id값들 null 체크
        if(userEmail != null)
            ReviewContollerValidator.validateUserEmail(userEmail); // 사용자 email 유효성 검사

        // 페이지 번호, 정렬 기준, 정렬 방향 유효성 검사
        ReviewContollerValidator.validatePageNumber(page);
        ReviewContollerValidator.validateSortValue(sortValue);
        ReviewContollerValidator.validateSortDirection(sortDirection);

        Page<ReviewWithLocationDto> reviews = reviewService.getReviewsWithLocationByEmail(userEmail, page, pageSize, sortValue, sortDirection);
        // Page<ReviewDto> 를 PagedModel<EntityModel<ReviewDto>>로 변환
        PagedModel<EntityModel<ReviewWithLocationDto>> pagedModel = pagedResourcesAssemblerWithLocation.toModel(reviews);

        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping("/uploadReviewImage")
    public ResponseEntity<List<String>> uploadProfileImage(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(name = "reviewId", required = false) Long reviewId,
            @RequestHeader("Authorization") String authorization
    ) throws IOException {
        log.info("GET /uploadReviewImage");

        // 이미지 파일을 저장하고 경로를 얻음
        List<String> savedImageUrls = reviewService.saveReviewImage(reviewId,files);
        return ResponseEntity.ok(savedImageUrls);
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


    /**
     * 저장된 이미지 파일을 서빙합니다
     * @param filename 파일이름
     * @return
     * @throws IOException
     */
    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable("filename") String filename) throws IOException {
        // 실제 파일 경로 설정 (서버 내 경로)
        Path filePath = Paths.get(uploadDir).resolve(filename).normalize();

        log.info("filename : {}",filename);
        // 파일이 존재하지 않으면 FileNotFoundException 던짐
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }

        // 파일을 UrlResource로 변환하여 반환
        Resource resource = new UrlResource(filePath.toUri());
        // 동적으로 MIME 타입 설정
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // 기본 MIME 타입 설정
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))  // MIME 타입을 실제 이미지에 맞게 설정
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }


}

