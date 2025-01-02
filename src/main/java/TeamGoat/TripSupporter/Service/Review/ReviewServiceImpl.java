package TeamGoat.TripSupporter.Service.Review;

import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewDto;
import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewWithLocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewWithUserProfileDto;
import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import TeamGoat.TripSupporter.Domain.Entity.Review.Review;
import TeamGoat.TripSupporter.Domain.Entity.Review.ReviewImage;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Domain.Enum.ReviewStatus;
import TeamGoat.TripSupporter.Exception.Review.ReviewException;
import TeamGoat.TripSupporter.Exception.Review.ReviewNotFoundException;
import TeamGoat.TripSupporter.Exception.UserNotFoundException;
import TeamGoat.TripSupporter.Exception.userProfile.UserProfileException;
import TeamGoat.TripSupporter.Mapper.Location.LocationMapper;
import TeamGoat.TripSupporter.Mapper.Review.ReviewMapper;
import TeamGoat.TripSupporter.Repository.Location.LocationRepository;
import TeamGoat.TripSupporter.Repository.Review.ReviewImageRepository;
import TeamGoat.TripSupporter.Repository.Review.ReviewRepository;
import TeamGoat.TripSupporter.Repository.User.UserRepository;
import TeamGoat.TripSupporter.Service.Review.Util.ReviewServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl{

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final ReviewMapper reviewMapper;

    @Value("${file.review-upload-dir}")
    private String uploadDir;
    @Value("${file.review-url-prefix}")
    private String urlPrefix;

//    private final String uploadDir = "upload/images/review/";  // 업로드 디렉토리 경로 (절대 경로 또는 상대 경로로 설정 가능)
//    private final String urlPrefix = "http://localhost:5050/reviews/images/";  // 이미지 접근 URL




    /**
     * 정렬기준에 따라 Pageable 객체 반환 (기본 정렬: ReviewCreatedAt DESC)
     * @param page : 시작 페이지 0부터 시작
     * @param sortValue : 정렬 기준 (ReviewCreatedAt, Rating)
     * @param sortDirection : 정렬 방향 (asc, desc)
     * @return : Pageable 객체
     */
    private Pageable getPageable(int page, int pageSize, String sortValue, String sortDirection) {
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

        Pageable pageable = PageRequest.of(page,pageSize,sort);
        ReviewServiceValidator.validatePageable(pageable);

        return pageable;
    }

    /**
     * 모든 활성화 상태의 데이터 검색
     * @param page : 시작 페이지 0부터 시작
     * @param sortValue : 정렬 기준 (ReviewCreatedAt, Rating), null 또는 비어있으면 ReviewCreatedAt기준
     * @param sortDirection : 정렬 방향 (asc, desc), null또는 비어있으면 내림차순
     * @return : 페이징처리된 리뷰 Dto
     */
    public Page<ReviewWithUserProfileDto> getReviews(int page, int pageSize, String sortValue, String sortDirection) {
        // 입력받은 값들로 pageable 객체 생성
        Pageable pageable = getPageable(page,pageSize,sortValue,sortDirection);
        // 생성된 pageable 객체를 Repository에 전달
        Page<Review> reviews = reviewRepository.findByReviewStatus(ReviewStatus.ACTIVE,pageable);

        log.info("모든 리뷰를 "+sortValue+" 기준으로" + sortDirection + " 방향으로 정렬합니다.");
        // 페이징 처리된 review를 Dto로 변환하여 반환
        return reviews.map(reviewMapper::ReviewConvertToWithUserProfileDto);
    }

    /**
     * 활성화 상태 리뷰중에서 여행지 기준 검색
     * @param locationId : 여행지아이디
     * @param page : 시작 페이지 0부터 시작
     * @param sortValue : 정렬 기준 (ReviewCreatedAt, Rating), null 또는 비어있으면 ReviewCreatedAt기준
     * @param sortDirection : 정렬 방향 (asc, desc), null또는 비어있으면 내림차순
     * @return : 페이징처리된 리뷰 Dto
     */
    public Page<ReviewWithUserProfileDto> getReviewsByLocationId(Long locationId, int page, int pageSize, String sortValue, String sortDirection) {
        // 입력받은 값들로 pageable 객체 생성
        Pageable pageable = getPageable(page,pageSize,sortValue,sortDirection);
        // locationId 유효성검사
        ReviewServiceValidator.validateLocationId(locationId);
        // 생성된 pageable 객체와 입력받은 locationId를 Repository에 전달
        Page<Review> reviews = reviewRepository.findByLocation_LocationIdAndReviewStatus(locationId,ReviewStatus.ACTIVE,pageable);

        log.info("Location에서 " + locationId + "로 검색한 결과를 "+sortValue+"기준으로 " + sortDirection + "방향으로 정렬합니다.");
        // 페이징 처리된 review를 반환 타입에 맞는 Dto로 변환하여 반환
        return reviews.map(reviewMapper::ReviewConvertToWithUserProfileDto);
    }

    /**
     * 활성화 상태 리뷰중에서 작성자 기준 검색
     * @param userEmail : 사용자정보를 찾을 사용자이메일
     * @param page : 시작 페이지 0부터 시작
     * @param sortValue : 정렬 기준 (ReviewCreatedAt, Rating), null 또는 비어있으면 ReviewCreatedAt기준
     * @param sortDirection : 정렬 방향 (asc, desc), null또는 비어있으면 내림차순
     * @return : 페이징처리된 리뷰 Dto
     */
    public Page<ReviewWithUserProfileDto> getReviewsByUserId(String userEmail, int page, int pageSize, String sortValue, String sortDirection) {
        // 입력받은 값들로 pageable 객체 생성
        Pageable pageable = getPageable(page,pageSize,sortValue,sortDirection);

        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(()->new UserNotFoundException("사용자를 찾을 수 없습니다."));

        Long userId = user.getUserId();
        // 생성된 pageable 객체와 입력받은 userId를 Repository에 전달
        Page<Review> reviews = reviewRepository.findByUser_UserIdAndReviewStatus(userId,ReviewStatus.ACTIVE,pageable);

        log.info("User에서 " + userId + "로 검색한 결과를 " + sortValue + " 기준으로" + sortDirection + " 방향으로 정렬합니다.");
        // 페이징 처리된 review를 Dto로 변환하여 반환
        return reviews.map(reviewMapper::ReviewConvertToWithUserProfileDto);
    }

    /**
     * 유저 email 받아서 review랑 그 review가 쓰인 location을 가져오는 메서드 review와 location정보는 페이징처리된 dto로 반환된다
     * @param userEmail
     * @param page
     * @param pageSize
     * @param sortValue
     * @param sortDirection
     * @return
     */
    public Page<ReviewWithLocationDto> getReviewsWithLocationByEmail(String userEmail,int page, int pageSize, String sortValue, String sortDirection) {

        Pageable pageable = getPageable(page, pageSize, sortValue, sortDirection);

        // userEmail로 user가져옴
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(()-> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        Long userId = user.getUserId();

        // 생성된 pageable 객체와 입력받은 userId를 Repository에 전달하여 review 가져옴
        Page<Review> reviews = reviewRepository.findByUser_UserIdAndReviewStatus(userId,ReviewStatus.ACTIVE,pageable);

        return reviews.map(review -> {
            // Review와 Location 정보를 DTO로 변환
            Location location = review.getLocation();  // Review 엔티티의 Location 객체
            LocationDto locationDto = locationMapper.toLocationDto(location);
            ReviewDto reviewDto = reviewMapper.ReviewConvertToDto(review);
            // ReviewWithLocationDto 생성
            return ReviewWithLocationDto.builder()
                    .reviewDto(reviewDto)
                    .locationDto(locationDto)
                    .build();
        });
    }

    /**
     * ReviewId로 Review와 Location을 찾아 함께 ReviewWithLocationDto로 반환합니다.
     * 입력받은 사용자정보의 userId와 review의 작성자인 userId가 다르다면 예외를 던집니다.
     * @param userEmail userEmail을 입력받아 user객체를 찾습니다
     * @param reviewId reviewId로 review를 찾습니다
     * @return ReviewWithLocationDto는 reviewDto와 locationDto를 결합한 Dto
     */
    public ReviewWithLocationDto getReviewWithLocationById(String userEmail, Long reviewId) {
        // reviewId로 review 가져옴
        Review review = reviewRepository.findByReviewIdAndReviewStatus(reviewId, ReviewStatus.ACTIVE)
                .orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾을 수 없습니다."));
        // userEmail로 user 가져옴
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(()-> new UserNotFoundException("사용자를 찾을 수 없습니다"));

        // 가져온 review의 상태가 유효한지 검사
        ReviewServiceValidator.isReviewStatusActive(review);

        // 가져온 review로부터 추출한 user와 userEmail로 가져온 user가 같은지 확인함
        ReviewServiceValidator.validateUserAndAuthor(review.getUser(),user);
        // Review로부터 location 가져옴
        Location location = review.getLocation();
        // 잘 가져왔는지 확인
        ReviewServiceValidator.validateLocationId(location.getLocationId());
        // 가져온 review와 location를 dto로 변환
        ReviewDto reviewDto = reviewMapper.ReviewConvertToDto(review);
        LocationDto locationDto = locationMapper.toLocationDto(location);

        // 가져온 리뷰를 dto로 변환해서 반환
        return new ReviewWithLocationDto(reviewDto, locationDto);
    }

    /**
     * 작성된 리뷰를 저장함
     * @param reviewDto 작성된 리뷰 데이터 객체
     */
    public void createReview(ReviewDto reviewDto){
        try {
            log.info("reviewService createReview invoke : " + reviewDto);
            // dto를 review객체로 변환
            Review review = reviewMapper.ReviewDtoConvertToEntity(reviewDto);
            log.info("reviewDto를 review엔티티로 변환");
            // 외래키 제대로 변환됐는지 확인
            ReviewServiceValidator.validateUserEmail(review.getUser().getUserEmail());
            ReviewServiceValidator.validateLocationId(review.getLocation().getLocationId());
            log.info("변환과정에서 review의 imageUrls가 어떤 상태인지 확인 :" + review.getImageUrls().size());

            // 검사가 끝난 후 저장
            reviewRepository.save(review);
            log.info("review를 성공적으로 저장하였습니다. 저장된 리뷰 아이디 : "+review.getReviewId());


        }catch (Exception e) {
            log.info("ReviewService Create Method invoke failed");
            log.info("reviewDto : {}",reviewDto);
            // 데이터베이스 관련 예외
            throw new ReviewException("Review 생성중 오류 발생",e);
        }
    }

    /**
     * 이미 작성된 리뷰를 불러와서 수정함
     * @param reviewDto 수정된 리뷰 데이터 객체
     */
    public void updateReview(String userEmail , ReviewDto reviewDto) {

        try{
            // 우선 ReviewDto의 id로 수정되기전 review를 가져온다.
            Review existingReview = reviewRepository.findById(reviewDto.getReviewId())
                    .orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾을 수 없습니다."));
            // 입력받은 userEmail로 사용자 정보를 가져온다
            User user = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(()->new UserNotFoundException("사용자를 찾을 수 없습니다."));


            // 수정되기전 review의 user정보와, 사용자가 일치하는지 확인한다
            ReviewServiceValidator.validateUserAndAuthor(user,existingReview.getUser());

            // 새로운 이미지 추가
            if (reviewDto.getImageUrls() != null) {
                // 기존 이미지 목록 제거 후 DTO에서 새 이미지 추가
                existingReview.getImageUrls().clear();  // 기존 이미지 삭제
                reviewDto.getImageUrls().forEach(existingReview::addImage);
            }

            // entity에서 update 메서드 실행 (title,Rating과 Comment 수정, UpdateAt 갱신)
            existingReview.updateReview(reviewDto.getTitle(),reviewDto.getRating(), reviewDto.getComment());
            reviewRepository.save(existingReview);

            log.info("review를 성공적으로 수정하였습니다. " +
                    "\n수정된 ReviewId : "+existingReview.getReviewId()+
                    "\n수정된 Rating : " + existingReview.getRating()+
                    "\n수정된 Comment : " + existingReview.getComment());

        }catch (Exception e) {
            // 데이터베이스 관련 예외
            throw new ReviewException("Review 수정중 오류 발생",e);
        }

    }

    /**
     * 삭제할 리뷰 id와 삭제하는 사용자의 id를 받아
     * 사용자 id와 작성자 id가 일치하는지 확인한 후 삭제
     * @param userEmail    삭제하는 사용자 email
     * @param reviewId  삭제할 리뷰 email
     */

    public void deleteReview(String userEmail,Long reviewId) {
        try{
            Review existingReview = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾을 수 없습니다."));

            User user = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));

            // 작성자와 user가 일치하는지 확인
            ReviewServiceValidator.validateUserAndAuthor(user,existingReview.getUser());

            reviewRepository.delete(existingReview);
            log.info(existingReview.getReviewId()+"의 삭제가 완료되었습니다.");

        }catch (Exception e) {
            // 데이터베이스 관련 예외
            throw new ReviewException("Review 삭제중 오류 발생",e);
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
            throw new ReviewException("rating 평균 계산중 오류 발생",e);
        }
    }

    public List<String> saveReviewImage(Long reviewId, List<MultipartFile> files) throws IOException {
        log.info("saveProfileImage is invoked");

        // 업로드할 파일들의 URL을 담을 리스트
        List<String> savedUrls = new ArrayList<>();

        // 각 파일에 대해 처리
        for (MultipartFile file : files) {
            if (file.getSize() > 5 * 1024 * 1024) {
                throw new IOException("파일 크기가 너무 큽니다. 최대 5MB까지 가능합니다.");
            }

            // 파일 확장자 확인
            String originalFilename = file.getOriginalFilename();
            log.info("originalFilename : {} ", originalFilename);


            if (originalFilename == null || !originalFilename.matches(".*\\.(jpg|jpeg|png)$")) {
                throw new IOException("지원되지 않는 파일 형식입니다.");
            }

            // 파일 이름 생성
            String fileName = System.currentTimeMillis() + "-" + UUID.randomUUID() + "-" + originalFilename;
            String directoryPath = uploadDir;
            log.info("fileName : {} , directoryPath : {}", fileName, directoryPath);

            // 파일 저장 경로 계산
            String absoluteDirPath = new File("").getAbsolutePath() + File.separator + directoryPath;
            log.info("absoluteDirPath : {}", absoluteDirPath);

            // 디렉토리 생성
            File directory = new File(absoluteDirPath);
            if (!directory.exists() && !directory.mkdirs()) {
                throw new IOException("디렉토리 생성에 실패했습니다.");
            }

            // 파일 저장 경로
            String imagePath = absoluteDirPath + File.separator + fileName;
            log.info("imagePath : {}", imagePath);

            // 파일 저장
            File dest = new File(imagePath);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                log.error("파일 저장 중 오류 발생: {}", e.getMessage(), e);
                throw new IOException("파일 저장에 실패했습니다.", e);
            }

            // 이미지 URL 생성
            String url = urlPrefix + fileName;
            log.info("url : {}", url);
            savedUrls.add(url);
        }

        // 리뷰를 수정하는 경우 ( 리뷰에 이전에 첨부된 이미지가 있다면 삭제 처리)
        if (reviewId != null) {
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new ReviewNotFoundException("리뷰를 찾을 수 없습니다."));
            // 리뷰로부터 기존 이미지 List 받아옴
            List<ReviewImage> existingImages = review.getImageUrls();
            // 리뷰에 이전에 첨부한 이미지 있는지 확인
            if (existingImages != null && !existingImages.isEmpty()) {
                // 각
                for(ReviewImage existingImage : existingImages){
                    String oldImageUrl = existingImage.getImageUrl(); // ReviewImage URL
                    String oldImagePath = oldImageUrl.replace(urlPrefix, ""); // URL에서 파일 경로 추출
                    File oldFile = new File(uploadDir, oldImagePath);
                    if (oldFile.exists() && oldFile.isFile()) {
                        boolean deleted = oldFile.delete();
                        log.info("이전 프로필 이미지 삭제 상태: {}", deleted ? "성공" : "실패");
                    }
                }
            }
        }

        return savedUrls;

    }
}

