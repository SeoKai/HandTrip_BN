package TeamGoat.TripSupporter.Domain.Dto.Planner;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
<<<<<<< HEAD
@ToString
=======
>>>>>>> eb2400e6ef8985be4db8b9249b3db945c5ea5104
public class DailyPlanDto {
    private Long dailyPlanId;

    private Long plannerId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;
}
