package TeamGoat.TripSupporter.Domain.Entity.Planner;

import TeamGoat.TripSupporter.Domain.Entity.Location.Location;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Entity
@Table(name = "tbl_todo")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long toDoId; // 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_plan_id", nullable = false)
    private DailyPlan dailyPlan; // 상위 하루 일정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location; // 방문할 장소

    @Builder
    public ToDo(DailyPlan dailyPlan, Location location) {
        this.dailyPlan = dailyPlan;
        this.location = location;
    }
}

