package TeamGoat.TripSupporter.Repository.Certification;


import TeamGoat.TripSupporter.Domain.Entity.Certification.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long>{
}
