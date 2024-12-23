package TeamGoat.TripSupporter.Domain.Dto.Location;

import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LocationParamDto {

    private String[] tagNames;
    private String keyword;
    private int page;
    private String sortValue;
    private String sortDirection;

}
