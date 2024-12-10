package TeamGoat.TripSupporter.Domain.Entity.Planner;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "TBL_DAILYPLAN")
@Getter
@ToString
<<<<<<< HEAD
@NoArgsConstructor(access = AccessLevel.PROTECTED)
=======
@Builder
>>>>>>> eb2400e6ef8985be4db8b9249b3db945c5ea5104
public class DailyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DAILYPLAN_ID")
    private Long dailyPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLANNER_ID", nullable = false)
    private Planner planner;    // 외래키

    @Column(name = "DAILYPLAN_SCHEDULE_DATE", nullable = false)
    private LocalDate scheduleDate; // 일정 날짜
<<<<<<< HEAD

    @Builder
    public DailyPlan(Long dailyPlanId, Planner planner, LocalDate scheduleDate) {
        this.dailyPlanId = dailyPlanId;
        this.planner = planner;
        this.scheduleDate = scheduleDate;
    }
=======
>>>>>>> eb2400e6ef8985be4db8b9249b3db945c5ea5104
}
