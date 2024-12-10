package TeamGoat.TripSupporter.Domain.Dto.Review;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReviewDto {
    private Long reviewId;

    private Long userId;

    private Long locationId;

    private double rating;

    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime reviewCreatedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime reviewUpdatedAt;
}
