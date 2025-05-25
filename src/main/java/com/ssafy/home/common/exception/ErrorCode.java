package com.ssafy.home.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	
	// 공통 & 일반 예외
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
    EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "외부 API 호출 중 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP 메서드입니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 엔티티를 찾을 수 없습니다."),
    
    // 인증 & 보안
    AUTH_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    AUTH_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 리소스에 접근 권한이 없습니다."),
    AUTH_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 잘못되었습니다."),
    AUTH_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "Access Token이 누락되었습니다."),
    AUTH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Access Token이 유효하지 않습니다."),
    AUTH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Access Token이 만료되었습니다."),
    AUTH_REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Refresh Token이 만료되었습니다."),
    AUTH_REFRESH_TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "DB의 Refresh Token과 일치하지 않습니다."),
    AUTH_CLAIM_MISSING(HttpStatus.UNAUTHORIZED, "JWT 클레임이 누락되었습니다."),
    AUTH_MISSING_CLAIM_USERNAME(HttpStatus.UNAUTHORIZED, "JWT 토큰에 username 클레임이 없습니다."),
    
    // INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다."),
    // TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Access Token이 만료되었습니다."),
    // REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Refresh Token이 만료되었습니다."),
    // REFRESH_TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "Refresh Token이 일치하지 않습니다."),

    // 사용자
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    USER_DUPLICATE(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
    USER_PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다."),
    
    // DB
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 처리 중 오류가 발생했습니다."),
	
	// Board
	BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글/댓글이 존재하지 않습니다."),
	BOARD_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 게시글/댓글에 대한 권한이 없습니다."),
	BOARD_WRITE_FORBIDDEN(HttpStatus.FORBIDDEN, "게시글/댓글 작성 권한이 없습니다."),
	BOARD_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN, "게시글/댓글 수정 권한이 없습니다."),
	BOARD_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "게시글/댓글 삭제 권한이 없습니다."),
	
	// ChatGPT(OpenAI)
	GPT_EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "GPT API 호출 중 오류가 발생했습니다."),
	GPT_RESPONSE_EMPTY(HttpStatus.BAD_GATEWAY, "GPT 응답이 비어 있습니다."),
	;
    
    
    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
