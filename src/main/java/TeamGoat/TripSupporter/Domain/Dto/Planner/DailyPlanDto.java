package TeamGoat.TripSupporter.Domain.Dto.Planner;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DailyPlanDto {
    private Long dailyPlanId;        // 하루 일정 ID
    private LocalDate planDate;      // 하루 날짜
    private List<ToDoDto> toDos;     // 하루 일정의 세부 장소 목록
}