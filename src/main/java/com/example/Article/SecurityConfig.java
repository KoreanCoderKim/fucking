package com.example.Article;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // CSRF 비활성화 (필요시 활성)
            .authorizeHttpRequests()
                .requestMatchers("/**").permitAll() // 모든 요청 허용
                .anyRequest().authenticated()
            .and()
            .formLogin().disable(); // 기본 로그인 폼 비활성화

        return http.build();
    }
}
