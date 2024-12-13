package TeamGoat.TripSupporter.Domain.Dto.Planner;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TodoDto {

    private Long todoId;              // 일정 고유 ID

    private Long dailyPlanId;         // 연결된 DailyPlan의 ID

    @DateTimeFormat(pattern = "HH:mm:ss") // 시간 형식 지정
    private LocalTime startTime;      // 일정 시작 시간

    @DateTimeFormat(pattern = "HH:mm:ss") // 시간 형식 지정
    private LocalTime endTime;        // 일정 종료 시간

    private String description;       // 일정 설명

    private Long locationId;          // 연결된 Location의 ID
}
