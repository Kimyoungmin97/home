package com.ssafy.home.domain.board.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CommentDto {
	private int comment_id;			// 댓글 ID
	private int post_id;			// 게시글 ID
	private int user_id;			// 작성자
	private int parent_id;			// 부모 댓글 ID (NULL이면 최상위 댓글)
	private String content;			// 댓글 내용
	private LocalDate created_at;	// 작성일시
	private LocalDate updated_at;	// 수정일시
}
