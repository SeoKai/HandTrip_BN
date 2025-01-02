package TeamGoat.TripSupporter.Domain.Entity.Planner;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_daily_plan")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_plan_id")
    private Long dailyPlanId; // 하루 일정 ID

    @Column(name = "plan_date", nullable = false)
    private LocalDate planDate; // 하루 날짜

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id", nullable = false)
    private Planner planner; // 상위 플래너

    @OneToMany(mappedBy = "dailyPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToDo> toDos = new ArrayList<>(); // 하루의 일정(ToDo) 목록

    @Builder
    public DailyPlan(LocalDate planDate, Planner planner) {
        this.planDate = planDate;
        this.planner = planner;
    }
}

