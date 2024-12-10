package TeamGoat.TripSupporter.Domain.Dto.Planner;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyPlanDto {
    private Long dailyPlanId;

    private Long plannerId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;
}
