package com.ssafy.home.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeAttachmentDto {
	private int attachmentId;
	private String name;
	private String url;
}
