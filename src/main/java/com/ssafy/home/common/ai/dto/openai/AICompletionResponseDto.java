package com.ssafy.home.common.ai.dto.openai;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AICompletionResponseDto {
	
	private String id;
	private String object;
	private Long created;	
	private String model;
	private List<Choice> choices;
	private Usage usage;
	
	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Choice{
		private Long index;
		private Message message;
	}
	
	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Message{
		private String role;
		private String content;
	}
	
	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Usage {
		private int prompt_tokens;
		private int completion_tokens;
		private int total_tokens;
	}

}