package com.ssafy.home.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistUserRequestDto {
	// 아이디, 비밀번호, 이메일, 닉네임, 거주지
	// 거주지(아파트명) -> apt_Seq 로 바꾸는 건가...
	private String username;	// 아이디
	private String password;	// 비밀번호
	private String email;		// 이메일
	private String name;		// 닉네임
	private String aptSeq; 		// 거주지 
}
