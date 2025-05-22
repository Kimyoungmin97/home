package com.ssafy.home.common.security.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.security.util.JwtTokenProvider;
import com.ssafy.home.domain.user.dto.User;
import com.ssafy.home.domain.user.service.UserService;
import com.ssafy.home.user.controller.ControllerHelper;

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
public class JwtController implements ControllerHelper {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    // /api/auth/refresh 요청 처리를 위한 handler 메서드
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("Refresh-Token") String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Refresh token is required"));
        }
        // 1. Refresh Token 유효성 검증 (JWT 자체) 및 이메일 추출
        Map<String, Object> claims = jwtTokenProvider.getClaims(refreshToken);
        String username = (String) claims.get("username"); // Refresh Token 생성 시 넣었던 클레임 키

        if (username == null) {
            throw new JwtException("Invalid refresh token: username claim missing");
        }

        // 2. DB에서 사용자 조회 및 Refresh Token 일치 여부 확인
        User user = userService.selectDetail(username);

        if (user == null || user.getRefreshToken() == null || !user.getRefreshToken().equals(refreshToken)) {
            log.warn("Invalid or mismatched refresh token for user: {}", username);
            // 보안: DB의 토큰과 불일치 시, 해당 사용자의 DB 토큰을 무효화(null 처리)하는 것도 고려
            // memberService.invalidateRefreshToken(email);
            return handleFail(new JwtException("Invalid refresh token"), HttpStatus.UNAUTHORIZED);
        }

        // 3. 새 Access Token 생성
        String newAccessToken = jwtTokenProvider.createAccessToken(user);

        // 4. Refresh Token Rotation: 새 Refresh Token 생성 및 DB 업데이트 - 보안 상 권장
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user);
        user.setRefreshToken(newRefreshToken); // Member 객체에 새 Refresh Token 설정
        userService.update(user); // DB에 새 Refresh Token 저장

        // 5. 새 토큰들을 응답으로 반환
        return handleSuccess(Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken));

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Refresh-Token") String refreshToken) {
        // /api/auth/logout 요청 시 refresh token에 해당하는 사용자 정보를 조회해서 정보를 수정 
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Refresh token is required"));
        }
        // 1. Refresh Token 유효성 검증 (JWT 자체) 및 로그인 아이디 추출
        Map<String, Object> claims = jwtTokenProvider.getClaims(refreshToken);
        String username = (String) claims.get("username"); // Refresh Token 생성 시 넣었던 클레임 키

        if (username == null) {
            throw new JwtException("Invalid refresh token: username claim missing");
        }

        // 2. DB에서 사용자 조회 및 Refresh Token 일치 여부 확인
        User member = userService.selectDetail(username);
        member.setRefreshToken(null);
        userService.update(member); // null 로 채워짐
        
        // 3. 새 토큰들을 응답으로 반환
        return handleSuccess(Map.of("accessToken", "", "refreshToken", ""));
        // END
    }
}
