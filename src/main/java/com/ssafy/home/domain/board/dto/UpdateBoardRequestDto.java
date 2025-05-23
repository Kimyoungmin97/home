package com.ssafy.home.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UpdateBoardRequestDto {
	private String title;		// 수정할 제목
	private String content;		// 내용
	
	@JsonProperty("isSecret")
	private Boolean isSecret;	// 비밀글 여부
}
