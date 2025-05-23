package com.ssafy.home.domain.board.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BoardListResponseDto {
	private int postId; 		// 게시글 ID
	private String aptSeq; 		// 대상 아파트 코드
	private int userId; 		// 작성자
	private String title;		// 제목
	private String content;		// 내용
	private boolean isSecret;	// 비밀글 여부
	private LocalDate createdAt;	// 작성일시
	private LocalDate updatedAt;	// 수정일시
}
