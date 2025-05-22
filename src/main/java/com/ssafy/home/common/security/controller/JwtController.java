package com.ssafy.home.common.security.controller;

import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.exception.ErrorCode;
import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.common.security.util.JwtTokenProvider;
import com.ssafy.home.domain.user.dto.User;
import com.ssafy.home.domain.user.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 로그인 요청 처리
// 사용자 정보 검증 (email/password)
// 성공 시 AccessToken + RefreshToken 발급해서 JSON 응답으로 반환

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class JwtController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    // /api/auth/refresh 요청 처리를 위한 handler 메서드
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("Refresh-Token") String refreshToken) {
        
    	try {
    		// 0. Refresh Token 비어 있는지 확인
    		if (refreshToken == null || refreshToken.isEmpty()) {
                throw new CustomException(ErrorCode.BAD_REQUEST);
            }
    		
    		// 1. Refresh Token 유효성 검증 (JWT 자체) 및 이메일 추출
            Map<String, Object> claims = jwtTokenProvider.getClaims(refreshToken);
            String username = (String) claims.get("username"); // Refresh Token 생성 시 넣었던 클레임 키
    	
            if (username == null) {
                throw new CustomException(ErrorCode.MISSING_CLAIM_USERNAME);
            }
            
            // 2. DB에서 사용자 조회 및 Refresh Token 일치 여부 확인
            User user = userService.selectDetail(username);
            
            if (user == null || user.getRefreshToken() == null || !user.getRefreshToken().equals(refreshToken)) {
                log.warn("Invalid or mismatched refresh token for user: {}", username);
                // 보안: DB의 토큰과 불일치 시, 해당 사용자의 DB 토큰을 무효화(null 처리)하는 것도 고려
                // memberService.invalidateRefreshToken(email);
                throw new CustomException(ErrorCode.REFRESH_TOKEN_MISMATCH); // Invalid refresh token
            }
            
            // 3. 새 Access Token 생성
            String newAccessToken = jwtTokenProvider.createAccessToken(user);
            // 4. Refresh Token Rotation: 새 Refresh Token 생성 및 DB 업데이트 - 보안 상 권장
            String newRefreshToken = jwtTokenProvider.createRefreshToken(user);
            user.setRefreshToken(newRefreshToken); // Member 객체에 새 Refresh Token 설정
            userService.update(user); // DB에 새 Refresh Token 저장

            // 5. 새 토큰들을 응답으로 반환
            return ResponseEntity.ok(ApiResponse.success(
            		Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken)
            ));
            
    	} catch (DataAccessException e) {
    		throw new CustomException(ErrorCode.DATABASE_ERROR);
    	} catch (ExpiredJwtException e) {
    		throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
    	} catch (JwtException e) {
    		throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
    	}
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Refresh-Token") String refreshToken) {
        // /api/auth/logout 요청 시 refresh token에 해당하는 사용자 정보를 조회해서 정보를 수정 
    	try {
    		if (refreshToken == null || refreshToken.isEmpty()) {
                throw new CustomException(ErrorCode.BAD_REQUEST);
            }
    		
    		// 1. Refresh Token 유효성 검증 (JWT 자체) 및 로그인 아이디 추출
            Map<String, Object> claims = jwtTokenProvider.getClaims(refreshToken);
            String username = (String) claims.get("username"); // Refresh Token 생성 시 넣었던 클레임 키

            if (username == null) {
                throw new CustomException(ErrorCode.MISSING_CLAIM_USERNAME);
            }
            
            // 2. DB에서 사용자 조회 및 Refresh Token 초기화
            User member = userService.selectDetail(username);
            member.setRefreshToken(null);
            userService.update(member); // null 로 채워짐
            
            // 3. 빈 토큰들을 응답으로 반환
            return ResponseEntity.ok(ApiResponse.success(
            		Map.of("accessToken", "", "refreshToken", "")
            ));

    	} catch (DataAccessException e) {
    		throw new CustomException(ErrorCode.DATABASE_ERROR);
    	} catch (ExpiredJwtException e) {
    		throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
    	} catch (JwtException e) {
    		throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
    	}
    }
}
