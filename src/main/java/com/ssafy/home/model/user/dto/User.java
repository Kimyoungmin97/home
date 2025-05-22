package com.ssafy.home.model.user.dto;

import java.time.LocalDateTime;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	private int userId;            // 회원 고유번호
	private @NonNull String username;           // 로그인 ID
	private @NonNull String password;           // 암호화 비밀번호
	private @NonNull String name;               // 이름
	
	private String role;
	private String refreshToken;
	
	private String email;              // 이메일
	private String aptSeq;             // 아파트 코드 (외래키)
	private String profileImage;       // 프로필 이미지 경로
	
	private LocalDateTime createdAt;   // 생성일시
	private LocalDateTime updatedAt;   // 수정일시
}
