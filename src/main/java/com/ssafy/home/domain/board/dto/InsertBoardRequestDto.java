package com.ssafy.home.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InsertBoardRequestDto {
	private String aptSeq;		// 대상 아파트 코드
	private String title;		// 제목
	private String content;		// 내용
	
	@JsonProperty("isSecret")
	private Boolean secret;	// 비밀글 여부
}
