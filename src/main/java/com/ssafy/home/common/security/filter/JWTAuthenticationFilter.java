package com.ssafy.home.common.security.filter;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.common.security.dto.CustomUserDetails;
import com.ssafy.home.common.security.util.JwtTokenProvider;
import com.ssafy.home.common.util.ControllerHelper;
import com.ssafy.home.domain.user.dto.User;
import com.ssafy.home.domain.user.service.UserService;

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
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements ControllerHelper {
	
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
		
		// 2. DB에 refreshToken 저장
		String accessToken = jwtTokenProvider.createAccessToken(user);
		String refreshToken = jwtTokenProvider.createRefreshToken(user);
		user.setRefreshToken(refreshToken);
		userService.update(user);
		
		// 3. 응답
		ApiResponse<?> body = ApiResponse.success(
			Map.of("accessToken", accessToken, "refreshToken", refreshToken)
		);
		// Spring 을 응답을 만들어주지 않아서 직접 생성해야 함
		// TODO: handleJsonResponse 만들어야 함
		handleJsonResponse(response, body, HttpStatus.OK);
		
//		Map<String, String> result = Map.of("status", "SUCCESS", "accessToken", accessToken, "refreshToken", refreshToken);
//		handleResult(response, result, HttpStatus.OK);
		
		return;
	}
	
	
	// 로그인 실패
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) {
    	// 예외 처리는 한 곳에서 처리하도록 전달
        throw failed;
    }
    // Filter 에서는 @RestController 나 ExceptionHandler 가 예외를 안 잡아줌
    // throw new CustomException(...) 해봤자 예외만 던져지고 응답은 클라이언트에게 전달이 안 됨
	
	
}
