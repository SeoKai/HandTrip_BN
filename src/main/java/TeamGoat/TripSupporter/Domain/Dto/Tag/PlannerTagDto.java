package TeamGoat.TripSupporter.Domain.Dto.Tag;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlannerTagDto {

    private Long plannerTagId;

    private Long plannerId; //참조하는 planner id

    private String tagId; //참조하는 tag id
}
