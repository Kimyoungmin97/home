package com.ssafy.home.common.ai.dto.openai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * GPT 응답 content 만 추출해서 응답으로 반환
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AIResponseDto {
	private String content;
}
