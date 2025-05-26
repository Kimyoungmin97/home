package com.ssafy.home.domain.notice.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeListResponseDto {
	private int noticeId;
	private String title;
	// @DateTimeFormat(pattern = "yyyy.MM.dd") <- 응답 들어올 때 사용
	@JsonFormat(pattern = "yyyy.MM.dd")
	private LocalDateTime createdAt;
	private boolean isImportant;
	private String content;
	
	private List<NoticeAttachmentDto> attachments;
	// private int authorId;
	// id, title, date, isImportant, content
}
