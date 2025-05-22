package com.ssafy.home.common.security.filter;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.home.common.security.dto.CustomUserDetails;
import com.ssafy.home.common.security.util.JwtTokenProvider;
import com.ssafy.home.domain.user.dto.User;
import com.ssafy.home.domain.user.service.UserService;
import com.ssafy.home.user.controller.ControllerHelper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 요청 처리 역할
 * 로그인 성공 시 JWT 생성 & 응답
 */
@Slf4j
@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements ControllerHelper{
	
	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider) {
		super(authenticationManager); // 조상 생성자 호출
		this.userService = userService;
		this.jwtTokenProvider = jwtTokenProvider;
		this.setFilterProcessesUrl("/api/auth/login"); // 로그인 URL 설정
		this.setUsernameParameter("username"); // username parameter 설정
		this.setPasswordParameter("password"); // password parameter 설정
	}
	
	/**
	 * 로그인 성공 -> JWT 발급
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// 1. token 전달
		CustomUserDetails details = (CustomUserDetails) authResult.getPrincipal();
		
		User user = details.getUser();
		
		String accessToken = jwtTokenProvider.createAccessToken(user);
		String refreshToken = jwtTokenProvider.createRefreshToken(user);
		user.setRefreshToken(refreshToken);
		userService.update(user);
		
		Map<String, String> result = Map.of("status", "SUCCESS", "accessToken", accessToken, "refreshToken", refreshToken);
		handleResult(response, result, HttpStatus.OK);
		
		return;
	}
	
	// 로그인 실패
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) {
        // 예외 처리는 한 곳에서 처리하도록 전달
        throw failed;
    }
	
	// 결과 전송 helper 메소드
    private void handleResult(HttpServletResponse response, Map<String, ?> data, HttpStatus status) {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(data);
            response.setStatus(status.value());
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            log.error("Error writing JSON response", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
