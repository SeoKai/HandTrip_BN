package TeamGoat.TripSupporter.Domain.Dto.Planner;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlannerDto {
    private Long plannerId;

    private String plannerTitle;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate plannerStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate plannerEndDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime plannerCreatedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime plannerUpdatedDate;

    private Long userId;
}
