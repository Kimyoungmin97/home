package com.ssafy.home.domain.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailResponseDto {
	private int postId; 		// 게시글 ID
	private String aptSeq; 		// 대상 아파트 코드
	private int userId; 		// 작성자
	private String title;		// 제목
	private String content;		// 내용
	private boolean isSecret;	// 비밀글 여부
	// @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdAt;	// 작성일시
	// @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime updatedAt;	// 수정일시
	
	// 댓글 목록
	private List<CommentResponseDto> comments;
}
