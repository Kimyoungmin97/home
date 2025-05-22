package com.ssafy.home.common.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.home.common.security.service.CustomUserDetailService;
import com.ssafy.home.common.security.util.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 토큰 인증 필터 (모든 요청에서 토큰 검사)
 * 요청 헤더에 실린 JWT 유효성 검증하고 SecurityContext 에 인증 정보 설정
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JWTVerificationFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		log.debug("JWTVerificationFilter.doFilterInternal() called");
		
		// 토큰 검증 & 인증 정보를 SecurityContextHolder에 저장
		String token = extractToken(request);
		if (token == null) { // 최초 로그인
			filterChain.doFilter(request, response);
			return;
		}
		
		// 토큰 검증 및 사용자 정보 추출 - 토큰에 문제 없다면 사용자 정보는 신뢰할만 함
		Claims claims = jwtTokenProvider.getClaims(token);

		UserDetails details = userDetailsService.loadUserByUsername(claims.get("username").toString());
		
		// 사용자 정보 조회
		var authentication = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
		
//		// 인증 객체 확인용 로그
//		log.debug("▶ 인증 객체 생성: {}", authentication);
//		log.debug("▶ 권한 목록: {}", authentication.getAuthorities());
		
		//  UserNameAuthenticationToken 생성 및 SecurityContextHolder에 저장
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//  다음 filter로 요청을 전달
        filterChain.doFilter(request, response);
	}
	
	private String extractToken(HttpServletRequest request) {
		
		// Authorization 헤더가 'Bearer '로 시작하는지 확인하고, Bearer를 제거한 토큰을 반환
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		return null;
	}

}
