package TeamGoat.TripSupporter.Domain.Dto.Tag;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
<<<<<<< HEAD
@ToString
=======
>>>>>>> eb2400e6ef8985be4db8b9249b3db945c5ea5104
public class PlannerTagDto {

    private Long plannerTagId;

    private Long plannerId; //참조하는 planner id

    private String tagId; //참조하는 tag id
}
