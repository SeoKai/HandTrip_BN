package TeamGoat.TripSupporter.Service.Auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private Map<String, Object> attributes;

    public CustomOAuth2User(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public <A> A getAttribute(String name) {
        return (A) attributes.get(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 기본 역할을 포함하도록 수정
        return List.of(() -> "ROLE_USER");
    }

    @Override
    public String getName() {
        // 'name' 속성이 없을 경우 기본값 반환
        return (String) attributes.getOrDefault("name", "이름 제공 안 함");
    }

    public String getEmail() {
        // 'email' 속성이 없을 경우 기본 메시지 반환
        return (String) attributes.getOrDefault("email", "이메일 제공 안 함");
    }

    public String getProvider() {
        return (String) attributes.getOrDefault("provider", "제공자 알 수 없음");
    }

    public String getProviderId() {
        return (String) attributes.getOrDefault("providerId", "제공자 ID 알 수 없음");
    }

    public String getPhone() {
        // 'mobile' 키를 사용하여 전화번호를 추출
        String phone = (String) attributes.get("mobile");
        if (phone == null) {
            // 'mobile_e164' 키로 대체 시도 (국제 표준 번호 형식)
            phone = (String) attributes.get("mobile_e164");
        }
        return phone != null ? phone : "번호 제공 안 함"; // 전화번호가 없으면 기본 메시지 반환
    }
}
