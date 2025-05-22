package com.ssafy.home.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ssafy.home.security.filter.JWTAuthenticationFilter;
import com.ssafy.home.security.filter.JWTVerificationFilter;
import com.ssafy.home.security.filter.SecurityExceptionHandlingFilter;
import com.ssafy.home.security.service.CustomUserDetailService;

// JWT 기반 REST API 보호 (/api/** 경로(헤더 기반 토큰 인증))
// 실제 /api/** 경로에 대한 보안 필터 체인 정의 (세부 정책)
@Configuration
public class APISecurityConfig {

    @Bean
    @Order(1) // 낮을 수록 우선순위가 높음. 생략 시 가장 낮음
    public SecurityFilterChain apiSecurityFilterChain(
            HttpSecurity http,
            @Qualifier("corsConfigurationSource") CorsConfigurationSource corsConfig,
            CustomUserDetailService userDetailsService,
            JWTAuthenticationFilter authFilter,
            JWTVerificationFilter jwtVerifyFilter,
            SecurityExceptionHandlingFilter exceptionFilter)
            throws Exception {
        // SecurityFilterChain을 생성
        // /api/**를 대상으로 하며 cors, userdetails, csrf, session 관련 설정을 처리
    	// /api/v1/etc/**, /api/auth/** 경로는 permitall 하고 나머지는(/api/v1/members) 인증을 요청한다.
    	http.securityMatcher("/api/**")
    		.cors(t -> t.configurationSource(corsConfig)) // 명시적 CORS 사용
    		.csrf(csrf -> csrf.disable()) // csrf 설정 무력화
    		.userDetailsService(userDetailsService)
    		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 설정 -> 세션 사용하지 않음(STATELESS)
    		.authorizeHttpRequests(auth -> auth
    				.requestMatchers(
    						"/api/auth/**",
    						"/api/houses/**"			
    						).permitAll()
    				.anyRequest().authenticated()
    		); 
    		
    	//  적절한 순서로 Filter의 위치를 지정
    	http.addFilterBefore(jwtVerifyFilter, UsernamePasswordAuthenticationFilter.class)
    		.addFilterAt(authFilter, UsernamePasswordAuthenticationFilter.class) // 대체
    		.addFilterBefore(exceptionFilter, JWTVerificationFilter.class);

        return http.build();
    }

    // Filter 수준에서 동작하기 위한 CorsConfigurationSource를 구성
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        // source.registerCorsConfiguration("/member/checkEmail", configuration);

        return source;
    }
    
    @Bean
    // Spring Security 5.4 이상에서 AuthenticationManager를 Bean으로 사용하려면 명시적 등록 필요
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
