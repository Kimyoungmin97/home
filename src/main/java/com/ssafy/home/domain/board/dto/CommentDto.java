package com.ssafy.home.domain.board.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
	private Long commentId;			// 댓글 ID
	private Long postId;			// 게시글 ID
	private int userId;				// 작성자
	private Long parentId;			// 부모 댓글 ID (NULL이면 최상위 댓글)
	private String content;			// 댓글 내용
	private LocalDate createdAt;	// 작성일시
	private LocalDate updatedAt;	// 수정일시
}
