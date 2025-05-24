package com.ssafy.home.common.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * OpenAI에 보낼 때 사용하는 하나의 "메시지" (role+content)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AIRequestMessageDto {
	
	@JsonProperty("role")
	private String role;	// "user", "assistant", "system"
	@JsonProperty("content")
	private String content;
	
}
