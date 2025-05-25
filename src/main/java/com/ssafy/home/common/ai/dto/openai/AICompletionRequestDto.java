package com.ssafy.home.common.ai.dto.openai;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 새로운 모델에 대한 프롬프트 요청 객체 : gpt-4, gpt-4 turbo, gpt-3.5-turbo
 * Completion : "GPT 응답 생성 요청"을 보내기 위한 데이터
 * 
 * ChatGPT API 요청용 전체 구조 (model, messages, temperature)
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AICompletionRequestDto {
	private String model;	// 사용 모델
	private Double temperature;	// 창의성 정도
	private List<AIRequestMessageDto> messages;	// 메시지 리스트
}
