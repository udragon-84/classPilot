package com.classpilot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/actuator/**").authenticated() // actuator 엔드포인트 인증 필요
                        .anyRequest()
                        .permitAll() // 그 외 모든 요청 허용
                )
                .httpBasic(withDefaults()) // 기본 HTTP 인증 사용
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
