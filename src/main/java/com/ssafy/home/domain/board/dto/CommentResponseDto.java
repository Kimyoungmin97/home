package com.ssafy.home.domain.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
	private Long commentId;				// 댓글 ID
	private Long userId;				// 작성자
	private Long parentId;				// 부모 댓글 ID (NULL이면 최상위 댓글)
	private String content;				// 댓글 내용
	private LocalDateTime createdAt;	// 작성일시
	private LocalDateTime updatedAt;	// 수정일시
}
