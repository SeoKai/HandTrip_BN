package TeamGoat.TripSupporter.Config.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.expiration}")
    private long accessExpiration; // Access Token 만료 시간

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration; // Refresh Token 만료 시간

    public long getAccessExpiration() {
        return accessExpiration;
    }

    public long getRefreshExpiration() {
        return refreshExpiration;
    }

    public String generateAccessToken(String userEmail) {
        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String generateRefreshToken(String userEmail) {
        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims validateAndExtractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, String userEmail) {
        try {
            Claims claims = validateAndExtractClaims(token);
            log.info("토큰 만료 시간: {}", claims.getExpiration());
            return claims.getSubject().equals(userEmail) && !claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.warn("만료된 토큰: {}", token);
            return false;
        } catch (Exception e) {
            log.error("토큰 유효성 검사 실패: {}", e.getMessage());
            return false;
        }
    }
    public String extractUserEmail(String token) {
        return validateAndExtractClaims(token).getSubject();
    }
}


