package TeamGoat.TripSupporter.Domain.Dto.Review;

import TeamGoat.TripSupporter.Domain.Enum.ReviewStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Valid
public class ReviewDto {

    @JsonProperty("reviewId")
    @NotNull(message = "Review ID는 null일 수 없습니다.")
    private Long reviewId;

    @JsonProperty("userEmail")
    private String userEmail;

    @JsonProperty("userNickname")
    private String userNickname;

    @JsonProperty("locationId")
    @NotNull(message = "Location ID는 null일 수 없습니다.")
    private Long locationId;

    @JsonProperty("title")
    @NotNull(message = "title은 null일 수 없습니다.")
    private String title;

    @JsonProperty("rating")
    @NotNull(message = "Rating은 null일 수 없습니다.")
    @Min(value = 1, message = "Rating은 1 이상이어야 합니다.")
    @Max(value = 5, message = "Rating은 5 이하이어야 합니다.")
    private Integer rating;

    @JsonProperty("comment")
    @Size(min = 1, max = 500, message = "Comment는 1자 이상, 500자 이하이어야 합니다.")
    private String comment;

    @JsonProperty("reviewCreatedAt")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime reviewCreatedAt;

    @JsonProperty("reviewUpdatedAt")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime reviewUpdatedAt;

    @JsonProperty("reviewStatus")
    @NotNull
    private ReviewStatus reviewStatus;

    private List<String> imageUrls; // 이미지 URL 목록 추가

    // 이미지 URL을 제외한 8개 필드만 포함하는 생성자
    public ReviewDto(Long reviewId, String userEmail, Long locationId, Integer rating, String comment,
                     LocalDateTime reviewCreatedAt, LocalDateTime reviewUpdatedAt,
                     ReviewStatus reviewStatus) {
        this.reviewId = reviewId;
        this.userEmail = userEmail;
        this.locationId = locationId;
        this.rating = rating;
        this.comment = comment;
        this.reviewCreatedAt = reviewCreatedAt;
        this.reviewUpdatedAt = reviewUpdatedAt;
        this.reviewStatus = reviewStatus;
        this.imageUrls = List.of(); // 기본값: 빈 리스트
    }

}
