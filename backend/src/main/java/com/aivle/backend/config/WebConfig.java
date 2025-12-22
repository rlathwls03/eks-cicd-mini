package com.aivle.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                 // 어떤 URL 패턴에 CORS를 적용할지
                .allowedOrigins(
                        "http://localhost:5173",
                        "http://k8s-default-frontend-6461c370e1-e88c0e301549ea3e.elb.us-east-2.amazonaws.com"
                ) // 허용할 프론트 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*")                    // 어떤 헤더를 허용할지
                .allowCredentials(true)                 // 쿠키/인증정보 허용할지
                .maxAge(3600);                          // Preflight 결과 캐시 시간(초)
    }
}
