package TeamGoat.TripSupporter.Domain.Dto.Location;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDto {
    private Long tagId;
    private String tagName;
}
