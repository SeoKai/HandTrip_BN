package TeamGoat.TripSupporter.Domain.Dto.User;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChangePasswordRequestDto {
    private String currentPassword;
    private String newPassword;
}
