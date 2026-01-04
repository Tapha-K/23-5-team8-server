package com.wafflestudio.team8server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // CSRF 방어 비활성화 (REST API용)
            .authorizeHttpRequests { auth ->
                // URL별 권한 설정
                auth
                    .requestMatchers("/api/auth/**") // /api/auth로 시작하는 모든 URL
                    .permitAll() // 인증 없이 접근 허용 (회원가입, 로그인)
                    .anyRequest() // 나머지 모든 요청
                    .authenticated() // 인증 필요
            }
        return http.build()
    }
}
