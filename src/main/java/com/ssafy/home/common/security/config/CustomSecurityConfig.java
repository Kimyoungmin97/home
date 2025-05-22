package com.ssafy.home.common.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

// 전역 설정 (@EnableMethodSecurity, PasswordEncoder, RoleHierarchy 등)
@EnableWebSecurity
@Configuration
@EnableMethodSecurity(
		securedEnabled = true, 	// @Secured("ROLE_ADMIN") 같은 거 활성화
		prePostEnabled = true 	// @PreAuthorize, @PostAuthorize 같은 거 활성화 -> @PreAuthorize("hasRole('ADMIN')")
)
public class CustomSecurityConfig {

    @Bean
    RoleHierarchy roleHierachy() {
        return RoleHierarchyImpl.withDefaultRolePrefix() // role의 기본 prefix 설정: ROLE_
                .role("ADMIN").implies("USER").role("USER").implies("GUEST").build();
    }

    @Bean
    PasswordEncoder passEncoder() {
        // 내부적으로 BCryptPasswordEncoder를 사용
        // return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    // 라이브 코드에서 세션 인증 기반 구조에서 사용되는 메서드들은 모두 지움
    // 쿠키 -> 프론트에서 처리...(하라고 GPT가) (STATELESS)
}
