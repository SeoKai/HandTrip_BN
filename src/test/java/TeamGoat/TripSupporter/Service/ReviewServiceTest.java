//package TeamGoat.TripSupporter.Service;
//
//import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewDto;
//import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewWithLocationDto;
//import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
//import TeamGoat.TripSupporter.Domain.Entity.Review.Review;
//import TeamGoat.TripSupporter.Domain.Entity.User.User;
//import TeamGoat.TripSupporter.Domain.Enum.ReviewStatus;
//import TeamGoat.TripSupporter.Repository.LocationRepository;
//import TeamGoat.TripSupporter.Repository.Review.ReviewRepository;
//import TeamGoat.TripSupporter.Repository.User.UserRepository;
//import TeamGoat.TripSupporter.Service.Review.ReviewServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.*;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)  // Mockito를 사용하기 위한 어노테이션
//public class ReviewServiceTest {
//
//    @InjectMocks
//    private ReviewServiceImpl reviewService; // 테스트할 대상인 서비스 클래스
//    @Mock
//    private ReviewRepository reviewRepository; // 실제 데이터베이스 접근을 대신할 모의 객체
//    @Mock
//    private UserRepository userRepository; // User 레포지토리 모의 객체
//    @Mock
//    private LocationRepository locationRepository; // Location 레포지토리 모의 객체
//    @Mock
//    private ReviewDto reviewDto; // 테스트 데이터로 사용할 ReviewDto 모의 객체
//
//    private User user;
//    private Location location;
//    private Review review;
//
//
//    @Test
//    public void createReview_Success() {
//        // Arrange
//        Long userId = 1L;
//        Long locationId = 10L;
//        ReviewDto reviewDto = new ReviewDto(1L ,userId, locationId, 4, "Great place!", null, null, ReviewStatus.ACTIVE);
//
//        // User와 Location 엔티티를 Mockito로 모킹합니다.
//        User user = mock(User.class);
//        Location location = mock(Location.class);
//
//        // 모킹된 객체의 행동 정의
//        when(user.getUserId()).thenReturn(userId);
//        when(location.getLocationId()).thenReturn(locationId);
//
//        // User와 Location이 각각 findById로 반환되도록 설정
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));
//
//        // Act
//        reviewService.createReview(reviewDto);
//
//        // Assert
//        verify(reviewRepository, times(1)).save(any(Review.class));
//        verify(userRepository, times(1)).findById(userId);
//        verify(locationRepository, times(1)).findById(locationId);
//    }
//
//    @Test
//    public void updateReview_Success() {
//        // Arrange
//        Long reviewId = 1L;
//        Long userId = 1L;
//        Long locationId = 10L;
//        ReviewDto reviewDto = new ReviewDto(1L, userId, locationId, 4, "Updated review text", null, null, ReviewStatus.ACTIVE);
//
//        // Mocking existing review, user, and location
//        Review existingReview = mock(Review.class);
//        User user = mock(User.class);
//        Location location = mock(Location.class);
//
//        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));
//
//        // Mocking the user in the existing review
//        when(existingReview.getUser()).thenReturn(user);  // Mock the user object in Review
//        when(user.getUserId()).thenReturn(userId);  // Ensure the user ID matches
//
//        // Act
//        reviewService.updateReview(reviewId, reviewDto);
//
//        // Assert
//        verify(reviewRepository, times(1)).save(any(Review.class));  // 수정된 리뷰가 저장되었는지 확인
//    }
//
//    @Test
//    void testDeleteReview_Success() {
//        // given
//        Long userId = 1L;
//        Long reviewId = 100L;
//
//        // Review 객체와 User 객체를 mock 설정
//        Review review = mock(Review.class);
//        User user = mock(User.class);
//
//        // Review 객체의 getUser()가 null을 반환하지 않도록 설정
//        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
//        when(review.getUser()).thenReturn(user);  // getUser()가 user를 반환하도록 설정
//        when(user.getUserId()).thenReturn(userId); // user.getUserId()가 userId를 반환하도록 설정
//
//        // when
//        reviewService.deleteReview(userId, reviewId);
//
//        // then
//        verify(reviewRepository, times(1)).delete(review);  // 리뷰 삭제가 한 번 호출되었는지 확인
//    }
//
//    @Test
//    public void testGetReviews_Success() {
//        // 필요한 객체들 모킹
//        Review review = mock(Review.class);
//        User user = mock(User.class);
//        Location location = mock(Location.class);
//
//        // getUser() 호출 시, mock된 user 반환하도록 설정
//        Mockito.when(review.getUser()).thenReturn(user);
//        Mockito.when(user.getUserId()).thenReturn(1L);  // getUserId()가 호출될 때 1L을 반환하도록 설정
//        Mockito.when(review.getLocation()).thenReturn(location);
//        Mockito.when(location.getLocationId()).thenReturn(10L);  // getLocationId()가 호출될 때 10L을 반환하도록 설정
//
//
//        // Pageable 설정
//        Pageable pageable = PageRequest.of(0, 5, Sort.by("reviewCreatedAt").descending());
//        Page<Review> mockPage = new PageImpl<>(List.of(review, review), pageable, 2);
//
//        // 모킹된 repository 반환 설정
//        Mockito.when(reviewRepository.findByReviewStatus(ReviewStatus.ACTIVE, pageable)).thenReturn(mockPage);
//
//        // 서비스 호출
//        Page<ReviewDto> result = reviewService.getReviews(0, "reviewCreatedAt", "desc");
//
//        // 결과 검증
//        assertNotNull(result);
//        assertEquals(2, result.getTotalElements());
//        assertEquals("reviewCreatedAt: DESC", result.getSort().toString().trim());
//    }
//
//    @Test
//    public void testGetReviewsByLocationId() {
//        // Mock 객체 생성
//        Long locationId = 1L;
//        Review review = mock(Review.class);
//        User user = mock(User.class);
//        Location location = mock(Location.class);
//
//        // Review와 User 설정
//        Mockito.when(review.getUser()).thenReturn(user);
//        Mockito.when(user.getUserId()).thenReturn(1L); // getUserId()가 호출될 때 1L을 반환하도록 설정
//        Mockito.when(review.getLocation()).thenReturn(location);
//        Mockito.when(location.getLocationId()).thenReturn(10L);  // getLocationId()가 호출될 때 10L을 반환하도록 설정
//
//        Pageable pageable = PageRequest.of(0, 5, Sort.by("reviewCreatedAt").descending());
//        Page<Review> mockPage = new PageImpl<>(List.of(review, review), pageable, 2);
//
//        // ReviewRepository의 findByLocation_LocationIdAndReviewStatus 모킹
//        Mockito.when(reviewRepository.findByLocation_LocationIdAndReviewStatus(locationId, ReviewStatus.ACTIVE, pageable)).thenReturn(mockPage);
//
//        // 서비스 메서드 호출
//        Page<ReviewDto> result = reviewService.getReviewsByLocationId(locationId, 0, "reviewCreatedAt", "desc");
//
//        // 결과 검증
//        assertNotNull(result);
//        assertEquals(2, result.getTotalElements());
//        assertEquals("reviewCreatedAt: DESC", result.getSort().toString());
//    }
//
//    @Test
//    public void testGetReviewsByUserId() {
//        // Mock 객체 생성
//        Long userId = 1L;
//        Review review = mock(Review.class);
//        User user = mock(User.class);
//        Location location = mock(Location.class);
//
//        // Review와 User 설정
//        Mockito.when(review.getUser()).thenReturn(user);
//        Mockito.when(user.getUserId()).thenReturn(1L); // getUserId()가 호출될 때 1L을 반환하도록 설정
//        Mockito.when(review.getLocation()).thenReturn(location);
//        Mockito.when(location.getLocationId()).thenReturn(10L);  // getLocationId()가 호출될 때 10L을 반환하도록 설정
//
//        Pageable pageable = PageRequest.of(0, 5, Sort.by("reviewCreatedAt").descending());
//        Page<Review> mockPage = new PageImpl<>(List.of(review, review), pageable, 2);
//
//        // ReviewRepository의 findByUser_UserIdAndReviewStatus 모킹
//        Mockito.when(reviewRepository.findByUser_UserIdAndReviewStatus(userId, ReviewStatus.ACTIVE, pageable)).thenReturn(mockPage);
//
//        // 서비스 메서드 호출
//        Page<ReviewDto> result = reviewService.getReviewsByUserId(userId, 0, "reviewCreatedAt", "desc");
//
//        // 결과 검증
//        assertNotNull(result);
//        assertEquals(2, result.getTotalElements());
//        assertEquals("reviewCreatedAt: DESC", result.getSort().toString());
//    }
//
//    @Test
//    public void testGetReviewWithLocationById() {
//        // Mock 객체 생성
//        Long reviewId = 1L;
//        Long userId = 1L;
//        User user = mock(User.class);
//        Review review = mock(Review.class);
//        Location location = mock(Location.class);
//
//        // 리뷰 상태 설정 ( ACTIVE로 설정)
//        Mockito.when(review.getReviewStatus()).thenReturn(ReviewStatus.ACTIVE); // 상태를 ACTIVE로 설정
//        Mockito.when(review.isActive()).thenReturn(true);
//
//        // location,user mock 설정
//        Mockito.when(review.getUser()).thenReturn(user);
//        Mockito.when(user.getUserId()).thenReturn(1L); // getUserId()가 호출될 때 1L을 반환하도록 설정
//        Mockito.when(review.getLocation()).thenReturn(location);
//        Mockito.when(location.getLocationId()).thenReturn(1L); // getLocationId()가 호출될 때 1L을 반환하도록 설정
//
//
//        // ReviewRepository의 findById 모킹
//        Mockito.when(reviewRepository.findByReviewIdAndReviewStatus(reviewId,ReviewStatus.ACTIVE)).thenReturn(Optional.of(review));
//
//        // 서비스 메서드 호출
//        ReviewWithLocationDto result = reviewService.getReviewWithLocationById(userId,reviewId);
//
//        // 결과 검증
//        assertNotNull(result);
//        assertEquals(1L, result.getLocationDto().getLocationId());
//    }
//
//
//
//
//
//
//
//
//
//
//}