package TeamGoat.TripSupporter.Service.Auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId(); // OAuth2 제공자 이름 (google, kakao, naver 등)
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());

        log.info("{} Attributes: {}", provider, attributes);

        String email = null;
        String providerId = null;

        // 각 제공자별 사용자 정보 처리
        if ("google".equals(provider)) {
            email = (String) attributes.get("email");
            providerId = (String) attributes.get("sub");

        } else if ("kakao".equals(provider)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");// Kakao의 경우 kakao_account에서 email을 추출
            email = (String) kakaoAccount.get("email");
            providerId = attributes.get("id").toString();

        } else if ("naver".equals(provider)) {
            attributes = (Map<String, Object>) attributes.get("response");// Naver의 경우 response 안에 사용자 정보가 포함됨
            email = (String) attributes.get("email");
            providerId = (String) attributes.get("id");
        }

        // 이메일 검증
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("OAuth2 인증에서 이메일을 찾을 수 없습니다.");
        }

        attributes.put("email", email);
        attributes.put("provider", provider);
        attributes.put("providerId", providerId);

        return new CustomOAuth2User(attributes);
    }
}
