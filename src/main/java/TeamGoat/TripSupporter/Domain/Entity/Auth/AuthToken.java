package TeamGoat.TripSupporter.Domain.Entity.Auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_tokens")
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    @Column(nullable = false, unique = true)
    private String userEmail; // 사용자 이메일

    @Column(nullable = false)
    private String refreshToken; // Refresh 토큰

    @Column(nullable = false)
    private Long expiration; // 토큰 만료 시간 (Unix Timestamp)


    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String providerId;
  
    public AuthToken(String userEmail, String refreshToken, long l) {
    }
}
