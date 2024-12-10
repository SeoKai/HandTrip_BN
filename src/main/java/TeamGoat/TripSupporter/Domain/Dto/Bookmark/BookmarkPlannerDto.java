package TeamGoat.TripSupporter.Domain.Dto.Bookmark;


import TeamGoat.TripSupporter.Domain.Dto.Planner.PlannerDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkPlannerDto {
    
    private Long bookmarkId;

    private Long plannerId;

    private Long userId;

}
