package TeamGoat.TripSupporter.Domain.Entity.Certification;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Table(name = "tbl_certification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_code_id")
    private Long verificationCodeId; // 고유 ID

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "code")
    private String code;

    @Column(name = "expiryTime")
    private LocalDateTime expiryTime; // 유효기간

    @Builder
    public VerificationCode(String email, String code, LocalDateTime expiryTime) {
        this.email = email;
        this.code = code;
        this.expiryTime = expiryTime;
    }

}