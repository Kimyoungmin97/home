package com.ssafy.home.security.util;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.home.model.user.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 토큰 생성/파싱/검증을 담당하는 유틸 클래스
 * - accessToken, refreshToken 생성
 * - Claims 추출 및 유효성 검증
 * - JWT 헤더 처리
 */
@Component
@Slf4j
public class JwtTokenProvider {
	private final SecretKey secretKey;
	
	public JwtTokenProvider() {
		secretKey = Jwts.SIG.HS256.key().build(); // secret key 자동 생성
		log.debug("JWT secret key: {}", Arrays.toString(secretKey.getEncoded()));
	}
	
	@Value("${jwt.access-expmin}")
	private long accessExpMin;
	
	@Value("${jwt.refresh-expmin}")
	private long refreshExpMin;
	
	// JWT 토큰 생성 & 반환
	public String create(String subject, long expMin, Map<String, Object> claims) {
		
		Date expDate = new Date(System.currentTimeMillis() + 1000 * 60 * expMin); // expMin -> 분 단위에서 초 단위로 변경
		String token = Jwts.builder()
				.subject(subject)	// token 의 제목(accessToken, refreshToken)
				.claims(claims)	// claims 에는 username(로그인 ID), name(이름), role 의 사용자 식별 정보(user)
				.expiration(expDate)
				.signWith(secretKey)
				.compact();
		
		return token;
	}
	
	/**
	 * accessToken 생성
	 */
	public String createAccessToken(User user) {
		// JWT 의 payload 는 보통 JSON 형태로 작성되어서, java 객체에서는 Map<String, Object> 객체를 JSON으로 직렬화해서 JWT 토큰 안에 집어넣게 되는데
		// JSON 직렬화 시 null 값이 있으면 에외가 날 수 있음 -> 사전 방어로 기본값 ("USER") 설정
		if (user.getRole()==null) user.setRole("USER");
		
		return create("accessToken", accessExpMin, 
				Map.of("username", user.getUsername(), "name", user.getName(), "role", user.getRole()));
	}
	
	/**
	 * refreshToken 생성
	 */
	public String createRefreshToken(User user) {		
		return create("refreshToken", refreshExpMin, Map.of("username", user.getUsername()));
	}
	
	/**
     * 토큰 검증 및 claim 정보 반환
     * 
     * @param jwt
     * @return
     * @throws ExpireJwtException:
     *             형식은 적합하지만 토큰의 유효기간이 지난 경우
     *             MalformedJwtException: 형식이 잘못된 토큰을 이용하려는 경우
     *             SignatureException: 훼손된 토큰을 이용하려는 경우
     */
	public Claims getClaims(String jwt) {

		var parser = Jwts.parser().verifyWith(secretKey).build();
		var jws = parser.parseSignedClaims(jwt);
		
		log.debug("claims: {}", jws.getPayload());
		
		return jws.getPayload(); // claim 반환
	}
}
