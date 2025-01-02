package TeamGoat.TripSupporter.Config;

import TeamGoat.TripSupporter.Config.auth.JwtAuthenticationFilter;
import TeamGoat.TripSupporter.Config.auth.JwtTokenProvider;
import TeamGoat.TripSupporter.Domain.Entity.Auth.AuthToken;
import TeamGoat.TripSupporter.Domain.Entity.User.User;
import TeamGoat.TripSupporter.Domain.Enum.UserRole;
import TeamGoat.TripSupporter.Domain.Enum.UserStatus;
import TeamGoat.TripSupporter.Repository.Auth.AuthTokenRepository;
import TeamGoat.TripSupporter.Repository.User.UserRepository;
import TeamGoat.TripSupporter.Service.Auth.CustomOAuth2User;
import TeamGoat.TripSupporter.Service.Auth.CustomOAuth2UserService;
import TeamGoat.TripSupporter.Service.Auth.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.RequestEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final AuthTokenRepository authTokenRepository;
    private final UserRepository userRepository;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService, CustomOAuth2UserService customOAuth2UserService, AuthTokenRepository authTokenRepository, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.customOAuth2UserService = customOAuth2UserService;
        this.authTokenRepository = authTokenRepository;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);


        http.cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 활성화
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                         // 인증 없이 접근 가능 경로
                        .anyRequest().permitAll() // 나머지 요청은 인증 필요
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService)) // OAuth2 사용자 서비스 설정
                        .successHandler((request, response, authentication) -> {
                            CustomOAuth2User customUser = (CustomOAuth2User) authentication.getPrincipal();
                            log.info("OAuth2 Success - Provider: {}, Email: {}", customUser.getProvider(), customUser.getEmail());
                            // 제공자 이름 추출
                            String provider = customUser.getProvider(); // "google", "naver" 등
                            String email = customUser.getEmail();       // 사용자 이메일
                            String providerId = customUser.getProviderId(); // Google의 sub 또는 Naver의 id
                            String name = customUser.getName();         // 사용자 이름
                            String phone = customUser.getPhone();       // 전화번호 (Google은 없을 수 있음)

                            if (phone == null) {
                                phone = "번호 제공 안 함"; // 기본값 설정
                            }

                            log.info("Provider: " + provider);
                            log.info("Email: " + email);
                            log.info("Provider ID: " + providerId);
                            log.info("Name: " + name);
                            log.info("Phone: " + phone);

                            // 사용자 저장 로직
                            Optional<User> optionalUser = userRepository.findByUserEmail(email);
                            if (optionalUser.isPresent()) {
                                User existingUser = optionalUser.get();
                                log.info("DB 에 존재하는 회원 : " + existingUser.getUserEmail());
                            } else {
                                User newUser = User.builder()
                                        .userEmail(email)
                                        .userPassword("") // 비밀번호 없음
                                        .userPhone(phone) // 전화번호 저장
                                        .userRole(UserRole.USER)
                                        .userStatus(UserStatus.ACTIVE)
                                        .provider(provider)
                                        .providerId(providerId)
                                        .build();
                                userRepository.save(newUser);
                                log.info("새로운 유저 : " + newUser.getUserEmail());
                            }

                            // JWT 토큰 생성 및 저장
                            String accessToken = jwtTokenProvider.generateAccessToken(email, provider);
                            String refreshToken = jwtTokenProvider.generateRefreshToken(email);

                            authTokenRepository.findByUserEmail(email).ifPresent(authTokenRepository::delete);
                            AuthToken authToken = new AuthToken();
                            authToken.setUserEmail(email);
                            authToken.setRefreshToken(refreshToken);
                            authToken.setProvider(provider);
                            authToken.setProviderId(providerId);
                            authToken.setExpiration(System.currentTimeMillis() + jwtTokenProvider.getRefreshExpiration());
                            authTokenRepository.save(authToken);


//                             JSON 데이터 반환
                            String redirectUrl = String.format(
                                    "http://localhost:3000/oauth2/callback?accessToken=%s&refreshToken=%s&accessTokenExpiry=%d&email=%s",
                                    accessToken, refreshToken, System.currentTimeMillis() + jwtTokenProvider.getAccessExpiration(), email
                            );
                            response.sendRedirect(redirectUrl);
                        })
//                            response.setContentType("application/json");
//                            response.setCharacterEncoding("UTF-8");
//
//                            Map<String, Object> responseBody = new HashMap<>();
//                            responseBody.put("accessToken", accessToken);
//                            responseBody.put("refreshToken", refreshToken);
//                            responseBody.put("accessTokenExpiry", System.currentTimeMillis() + jwtTokenProvider.getAccessExpiration());
//                            responseBody.put("email", email);
//
//                            new ObjectMapper().writeValue(response.getWriter(), responseBody);
//                        })


                        .failureHandler((request, response, exception) -> {
                            log.error("OAuth2 Failure - Error: {}", exception.getMessage());
                            response.sendRedirect("http://localhost:3000/login?error=true");
                        }));
        return http.build();
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .refreshToken()
                        .clientCredentials()
                        .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService);

        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        // 클라이언트 시크릿 속성 추가
        authorizedClientManager.setContextAttributesMapper(context -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("client_secret", "g8UFiKIUaSHdhubQVLoatcaAcw9B9jSJ"); // 클라이언트 시크릿 직접 정의
            return attributes;
        });

        return authorizedClientManager;
    }





    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    // cors 문제 해결용 메서드
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // React 서버
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
