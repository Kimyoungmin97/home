package com.ssafy.home.domain.board.dto;

import lombok.Data;

@Data
public class InsertCommentRequestDto {
	private Long parentId;		// 대댓글이면 값, 일반 댓글이면 null
	private String content;		// 내용
}
