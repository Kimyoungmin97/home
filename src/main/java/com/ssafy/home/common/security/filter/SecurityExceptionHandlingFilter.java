package com.ssafy.home.common.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.home.common.exception.ErrorCode;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityExceptionHandlingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
        	// 1. JWT 관련 예외 처리
        	if (e instanceof ExpiredJwtException) {
                setErrorResponse(response, ErrorCode.AUTH_TOKEN_EXPIRED);
            } else if (e instanceof JwtException) {
            	setErrorResponse(response, ErrorCode.AUTH_TOKEN_INVALID);
            }
        	// 2. 인증 실패 (로그인 정보 잘못 입력 등)
        	else if (e instanceof BadCredentialsException) { 
                setErrorResponse(response, ErrorCode.AUTH_INVALID_CREDENTIALS);
            } else if (e instanceof AuthenticationException) {
            	setErrorResponse(response, ErrorCode.AUTH_UNAUTHORIZED);
            }
        	// 3. 인가 실패 (권한 없음)
            else if (e instanceof AccessDeniedException || e instanceof AuthorizationDeniedException) {
        		setErrorResponse(response, ErrorCode.AUTH_FORBIDDEN);
        	}
        	// 4. 기타 예외 처리
        	else { 
                setErrorResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
    	
    	log.debug("Spring Security 응답 처리: {}, {}", errorCode.getStatus(), errorCode.getMessage());
    	
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // default utf-8
        response.setCharacterEncoding("UTF-8");
        
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", errorCode.getStatus().value());
        errorDetails.put("message", errorCode.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }
}
