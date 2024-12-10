package TeamGoat.TripSupporter.Domain.Entity.Planner;

import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "TBL_TODO")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TODO_ID")
    private Long todoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DAILYPLAN_ID", nullable = false)
    private DailyPlan dailyPlan;    // fk

    @Column(name = "TODO_START_TIME", nullable = false)
    private LocalTime startTime;    // 일정 시작 시간

    @Column(name = "TODO_END_TIME", nullable = false)
    private LocalTime endTime;  // 일정 종료 시간

    @Column(name = "TODO_DESCRIPTION", columnDefinition = "TEXT")
    private String description; // 일정에 대한 설명

    @OneToOne  // 1:1 관계 매핑
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID", nullable = true)
    private Location locationId;      // 위치 참조 (Location fk)

    @Builder
    public Todo(Long todoId, DailyPlan dailyPlan, LocalTime startTime, LocalTime endTime, String description, Location locationId) {
        this.todoId = todoId;
        this.dailyPlan = dailyPlan;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.locationId = locationId;
    }


}
