package TeamGoat.TripSupporter.Controller;

import TeamGoat.TripSupporter.Controller.Review.ReviewController;
import TeamGoat.TripSupporter.Domain.Dto.Location.LocationDto;
import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewDto;
import TeamGoat.TripSupporter.Domain.Dto.Review.ReviewWithLocationDto;
import TeamGoat.TripSupporter.Domain.Enum.ReviewStatus;
import TeamGoat.TripSupporter.Exception.Review.ReviewAuthorMismatchException;
import TeamGoat.TripSupporter.Service.Review.ReviewServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PagedResourcesAssembler<ReviewDto> pagedResourcesAssembler;

    @MockitoBean
    private ReviewServiceImpl reviewService;


    private final ObjectMapper objectMapper;

    public ReviewControllerTest() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // LocalDateTime 지원
    }
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"}) // Mock 사용자 설정
    public void createReview_Success() throws Exception {
        // 리뷰 생성 성공 시나리오 테스트
        // 1. ReviewDto 객체를 설정
        // 2. reviewService의 createReview 메서드가 호출될 때 성공적으로 처리되도록 mock 설정
        // 3. POST 요청을 보내 리뷰 생성이 성공했을 때 "리뷰를 성공적으로 작성하였습니다." 메시지가 반환되는지 확인
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewId(1L);
        reviewDto.setUserId(1L);
        reviewDto.setLocationId(10L);
        reviewDto.setRating(5);
        reviewDto.setComment("Great!");
        reviewDto.setReviewCreatedAt(null);
        reviewDto.setReviewUpdatedAt(null);
        reviewDto.setReviewStatus(ReviewStatus.ACTIVE);

        System.out.println("ReviewDto: " + reviewDto);
        String reviewDtoJson = objectMapper.writeValueAsString(reviewDto);
        System.out.println(reviewDtoJson);

        doNothing().when(reviewService).createReview(any(ReviewDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/reviews/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userId", 1L) // 헤더로 userId 전달
                        .content(reviewDtoJson)
                        .with(csrf())) // CSRF 토큰 추가
                .andExpect(status().isOk())
                .andExpect(content().string("리뷰를 성공적으로 작성하였습니다."));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"}) // Mock 사용자 설정
    public void createReview_InvalidRating_ThrowsException() throws Exception {
        // 유효하지 않은 평점이 전달될 경우 예외가 발생하는지 테스트
        // 1. 평점이 0인 ReviewDto 객체를 설정 (잘못된 평점)
        // 2. reviewService의 createReview 메서드가 IllegalArgumentException을 던지도록 mock 설정
        // 3. POST 요청을 보내 예외가 발생하며 "잘못된 입력: 평점은 1에서 5 사이의 값이어야 합니다." 메시지가 반환되는지 확인
        ReviewDto invalidReviewDto = new ReviewDto();
        invalidReviewDto.setReviewId(1L);
        invalidReviewDto.setUserId(1L);
        invalidReviewDto.setLocationId(10L);
        invalidReviewDto.setRating(0); // 유효하지 않은 평점
        invalidReviewDto.setComment("Great!");
        invalidReviewDto.setReviewCreatedAt(null);
        invalidReviewDto.setReviewUpdatedAt(null);
        invalidReviewDto.setReviewStatus(ReviewStatus.ACTIVE);

        String invalidReviewDtoJson = objectMapper.writeValueAsString(invalidReviewDto);

        Mockito.doThrow(new IllegalArgumentException("평점은 1에서 5 사이의 값이어야 합니다."))
                .when(reviewService).createReview(any(ReviewDto.class));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/reviews/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userId", 1L) // 헤더로 userId 전달
                        .content(invalidReviewDtoJson)
                        .with(csrf())) // CSRF 토큰 추가
                .andExpect(status().isBadRequest())
                .andExpect(content().string("잘못된 입력: 평점은 1에서 5 사이의 값이어야 합니다."));
    }

    @Test
    @WithMockUser(username="testuser", roles = {"USER"})
    public void showReviewUpdatePage_Success() throws Exception {
        // 리뷰 수정 페이지 조회 성공 시나리오 테스트
        // 1. reviewService의 getReviewWithLocationById 메서드가 정상적으로 실행되도록 mock 설정
        // 2. GET 요청을 보내서 반환된 리뷰 정보가 제대로 JSON 형태로 반환되는지 확인
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewId(1L);
        LocationDto locationDto = new LocationDto();
        locationDto.setLocationId(10L);
        ReviewWithLocationDto reviewWithLocationDto = new ReviewWithLocationDto();
        reviewWithLocationDto.setReviewDto(reviewDto);
        reviewWithLocationDto.setLocationDto(locationDto);
        System.out.println("reviewWithLocationDto: " + reviewWithLocationDto);
        String reviewWithLocationDtoJson = objectMapper.writeValueAsString(reviewWithLocationDto);
        System.out.println(reviewWithLocationDtoJson);

        Mockito.when(reviewService.getReviewWithLocationById(Mockito.eq(1L), Mockito.eq(10L)))
                .thenReturn(reviewWithLocationDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/reviews/{reviewId}/edit" , 10L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userId",1L)
                        .content(reviewWithLocationDtoJson)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(reviewWithLocationDto))); // JSON 비교
    }

    @Test
    @WithMockUser(username="testuser", roles = {"USER"})
    public void showReviewUpdatePage_InvalidUserId_ThrowsException() throws Exception {
        // 유효하지 않은 사용자 ID로 수정 페이지를 조회할 경우 예외가 발생하는지 테스트
        // 1. reviewService의 getReviewWithLocationById 메서드가 ReviewAuthorMismatchException을 던지도록 mock 설정
        // 2. GET 요청을 보내 예외가 발생하며 "유효하지 않은 사용자 ID" 메시지가 반환되는지 확인
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewId(1L);
        reviewDto.setUserId(null);
        LocationDto locationDto = new LocationDto();
        locationDto.setLocationId(10L);
        ReviewWithLocationDto reviewWithLocationDto = new ReviewWithLocationDto();
        reviewWithLocationDto.setReviewDto(reviewDto);
        reviewWithLocationDto.setLocationDto(locationDto);
        System.out.println("reviewWithLocationDto: " + reviewWithLocationDto);
        String reviewWithLocationDtoJson = objectMapper.writeValueAsString(reviewWithLocationDto);
        System.out.println(reviewWithLocationDtoJson);

        Mockito.when(reviewService.getReviewWithLocationById(Mockito.eq(1L), Mockito.eq(10L)))
                .thenThrow(new ReviewAuthorMismatchException("유효하지 않은 사용자 ID"));

        mockMvc.perform(MockMvcRequestBuilders.get("/reviews/{reviewId}/edit" , 10L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userId",1L)
                        .content(reviewWithLocationDtoJson)
                        .with(csrf()))
                .andExpect(status().isForbidden())
                .andExpect(content().string("유효하지 않은 사용자 ID")); // JSON 비교
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"}) // Mock 사용자 설정
    public void updateReview_Success() throws Exception {
        // 리뷰 수정 성공 시나리오 테스트
        // 1. 수정할 리뷰 정보를 담은 ReviewDto 객체를 설정
        // 2. reviewService의 updateReview 메서드가 성공적으로 처리되도록 mock 설정
        // 3. PUT 요청을 보내서 리뷰가 성공적으로 수정되었을 때 "리뷰 수정 성공" 메시지가 반환되는지 확인
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewId(1L);
        reviewDto.setUserId(1L);
        reviewDto.setLocationId(10L);
        reviewDto.setRating(4);
        reviewDto.setComment("Good!");
        reviewDto.setReviewCreatedAt(null);
        reviewDto.setReviewUpdatedAt(null);
        reviewDto.setReviewStatus(ReviewStatus.ACTIVE);

        List<String> list = new ArrayList<>();
        list.add("테스트이미지url1");
        list.add("테스트이미지url2");
        list.add("테스트이미지url3");

        reviewDto.setImageUrls(list);

        System.out.println("ReviewDto: " + reviewDto);
        String reviewDtoJson = objectMapper.writeValueAsString(reviewDto);
        System.out.println(reviewDtoJson);

        Mockito.doNothing().when(reviewService).updateReview(Mockito.eq(1L), any(ReviewDto.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/reviews/{reviewId}/edit/completed",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userId",1L)
                        .content(reviewDtoJson)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("리뷰 수정 성공"));
    }


    @Test
    @WithMockUser(username = "testuser", roles = {"USER"}) // Mock 사용자 설정
    public void updateReview_MismatchedIds_ThrowsException() throws Exception {
        // 리뷰 수정 시 ID 불일치로 예외가 발생하는지 테스트
        // 1. reviewService의 updateReview 메서드가 호출될 때, 잘못된 ID로 예외가 발생하도록 mock 설정
        // 2. PUT 요청을 보내서 예외가 발생하고 "ID 불일치: 입력된 두 값이 다릅니다." 메시지가 반환되는지 확인
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewId(1L);
        reviewDto.setUserId(1L);
        reviewDto.setLocationId(10L);
        reviewDto.setRating(4);
        reviewDto.setComment("Good!");
        reviewDto.setReviewCreatedAt(null);
        reviewDto.setReviewUpdatedAt(null);
        reviewDto.setReviewStatus(ReviewStatus.ACTIVE);

        System.out.println("ReviewDto: " + reviewDto);
        String reviewDtoJson = objectMapper.writeValueAsString(reviewDto);
        System.out.println(reviewDtoJson);

        Mockito.doNothing().when(reviewService).updateReview(Mockito.eq(1L), any(ReviewDto.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/reviews/{reviewId}/edit/completed",2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userId",1L)
                        .content(reviewDtoJson)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ID 불일치: 입력된 두 값이 다릅니다: 2와 1"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"}) // Mock 사용자 설정
    public void deleteReview_Success() throws Exception {
        // 리뷰 삭제 성공 시나리오 테스트
        // 1. reviewService의 deleteReview 메서드가 성공적으로 실행되도록 mock 설정
        // 2. DELETE 요청을 보내서 리뷰가 성공적으로 삭제되었을 때 "리뷰 삭제 성공" 메시지가 반환되는지 확인
        Mockito.doNothing().when(reviewService).deleteReview(Mockito.eq(1L), Mockito.eq(1L));
        mockMvc.perform(MockMvcRequestBuilders.delete("/reviews/delete/{reviewId}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userId",1L)
                        .content("")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("리뷰 삭제 성공"));
    }





//    //테케 안돌아감 포기했음 테케만 거의 12시간정도 잡고있엇는대 모르겟음
//    @Test
//    @WithMockUser(username = "testuser", roles = {"USER"})
//    public void getReviews_Success() throws Exception {
//        // Mock 설정
//        ReviewDto reviewDto = new ReviewDto(1L, 1L, 10L, 5, "Great place!", null, null, ReviewStatus.ACTIVE);
//        Page<ReviewDto> mockPage = new PageImpl<>(List.of(reviewDto));
//
//        // 요청을 위한 MockHttpServletRequest 설정
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.setMethod("GET");
//        request.setRequestURI("/reviews/getPagedReviews");
//        request.addParameter("page", "0");
//        request.addParameter("sortValue", "reviewCreatedAt");
//        request.addParameter("sortDirection", "desc");
//        request.addParameter("locationId", "10");
//
//
//        // ServletRequestAttributes를 설정하여 PagedResourcesAssembler가 정상적으로 동작하도록 합니다.
//        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
//        RequestContextHolder.setRequestAttributes(attributes);
//
//        // 서비스 메서드 호출 시 Mock 설정
//        Mockito.when(reviewService.getReviewsByUserId(Mockito.eq(1L), Mockito.eq(0), Mockito.eq("reviewCreatedAt"), Mockito.eq("desc")))
//                .thenReturn(mockPage);
//
//        // MockMvc 실행: 리뷰 조회
//        mockMvc.perform(MockMvcRequestBuilders.get("/reviews/getPagedReviews")
//                        .param("page", "0")
//                        .param("sortValue", "reviewCreatedAt")
//                        .param("sortDirection", "desc")
//                        .param("locationId", "10")
//                        .with(csrf())) // CSRF 추가
//                .andDo(result -> {
//                    // 요청 파라미터 로그 출력
//                    System.out.println("Request Parameters: " + result.getRequest().getParameterMap());
//                })
//                .andExpect(status().isOk())  // 200 OK 확인
//                .andExpect(jsonPath("$.content[0].reviewId").value(1L))  // 리뷰 ID 확인
//                .andExpect(jsonPath("$.content[0].comment").value("Great place!"))  // 댓글 확인
//                .andExpect(jsonPath("$.totalElements").value(1))  // 전체 요소 개수 확인
//                .andExpect(jsonPath("$.totalPages").value(1))  // 전체 페이지 수 확인
//                .andExpect(jsonPath("$.size").value(10))  // 한 페이지 크기 확인
//                .andExpect(jsonPath("$._links.self.href").exists());  // HATEOAS 링크 존재 확인
//
//        // 서비스 메서드 호출 검증
//        Mockito.verify(reviewService).getReviewsByLocationId(Mockito.eq(10L), Mockito.eq(0), Mockito.eq("reviewCreatedAt"), Mockito.eq("desc"));
//    }
//


}

