package TeamGoat.TripSupporter.Domain.Dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {

    private String accessToken;  // Access 토큰
    private String refreshToken; // Refresh 토큰
}
