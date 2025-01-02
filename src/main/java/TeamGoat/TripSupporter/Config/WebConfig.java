package TeamGoat.TripSupporter.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
                        .allowedOrigins("http://localhost:3000") // React 개발 서버 URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                        .allowedHeaders("*"); // 모든 헤더 허용
            }
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/favicon.ico")
                        .addResourceLocations("classpath:/static/favicon.ico")
                        .setCachePeriod(3600);
            }
        };
    }
}
