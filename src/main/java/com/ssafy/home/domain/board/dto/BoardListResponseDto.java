package com.ssafy.home.domain.board.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 일단 posts 에 있는 모든 항목 다 가져오는데 나중에 필요 시 몇 개는 생략
 */
@Data
public class BoardListResponseDto {
	private int postId; 		// 게시글 ID
	private String aptSeq; 		// 대상 아파트 코드
	private int userId; 		// 작성자
	private String title;		// 제목
	private String content;		// 내용
	private boolean isSecret;	// 비밀글 여부
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdAt;	// 작성일시
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime updatedAt;	// 수정일시
}
