package com.ssafy.home.common.ai.dto.openai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * OpenAI에 보낼 때 사용하는 하나의 "메시지" (role+content)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AIRequestMessageDto {
	
	private String role;	// "system", "user"
	private String content;
	
}
