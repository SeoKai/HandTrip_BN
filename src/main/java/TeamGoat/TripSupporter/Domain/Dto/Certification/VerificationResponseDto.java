package TeamGoat.TripSupporter.Domain.Dto.Certification;

import lombok.*;
import org.hibernate.annotations.SecondaryRow;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationResponseDto {

    private String identityVerificationId;
    private String code;
    private String message;

}
