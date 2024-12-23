package TeamGoat.TripSupporter.Service.Auth;

import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Repository.User.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        // 사용자 정보를 데이터베이스에서 조회
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // UserDetails 객체 반환
        return org.springframework.security.core.userdetails.User.withUsername(user.getUserEmail())
                .password(user.getUserPassword()) // 암호화된 비밀번호 사용
                .roles(user.getUserRole().name()) // 역할(Role) 설정
                .build();
    }
}
