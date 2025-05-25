package com.ssafy.home.common.ai.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.home.common.ai.dto.openai.AICompletionRequestDto;
import com.ssafy.home.common.ai.dto.openai.AICompletionResponseDto;
import com.ssafy.home.common.ai.dto.openai.AIRequestMessageDto;
import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 프롬프트 생성 + OpenAI 요청 처리 서비스
 * PromptType으로 system/user 메시지 구성
 * RestTemplate 통해 OpenAI 호출
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AIPromptService {
	
	private final RestTemplate restTemplate;
	private final HttpHeaders httpHeaders;
	private final ObjectMapper objectMapper;
	
	@Value("${chatgpt.url.prompt}")
    private String promptUrl;
	
	@Value("${chatgpt.model}")
	private String model;
	
	@Value("${chatgpt.temperature}")
	private Double temperature;
	
	/**
	 * (1) enum 에 따라 프롬프트 메시지 다르게 적용
	 * (2) 분석 유형마다 입력 DTO 를 분기해서 처리
	 */
	
	public Map<String, Object> analyze(String systemPrompt, String userPrompt) {
        try {
            AICompletionRequestDto request = buildCompletionRequest(systemPrompt, userPrompt);
            HttpEntity<AICompletionRequestDto> entity = new HttpEntity<>(request, httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(
            		promptUrl, HttpMethod.POST, entity, String.class
            );

            log.info("[GPT 요청] {}", entity.getBody());
            log.info("[GPT 응답] {}", response.getBody());
            
            AICompletionResponseDto responseDto = objectMapper.readValue(response.getBody(), AICompletionResponseDto.class);
            if (responseDto == null || responseDto.getChoices().isEmpty()) {
            	throw new CustomException(ErrorCode.GPT_RESPONSE_EMPTY);
            }
         
            String content = responseDto.getChoices().get(0).getMessage().getContent();
            return Map.of("content", content);
            
        } catch (Exception e) {
        	log.error("[GPT 호출 실패] systemPrompt: {}, userPrompt: {}", systemPrompt, userPrompt, e);
            throw new CustomException(ErrorCode.GPT_EXTERNAL_API_ERROR);
        }
    }
	
	private AICompletionRequestDto buildCompletionRequest(String systemPrompt, String userPrompt) {
		AIRequestMessageDto systemMessage = AIRequestMessageDto.builder()
		        .role("system").content(systemPrompt).build();
		
		AIRequestMessageDto userMessage = AIRequestMessageDto.builder()
		        .role("user").content(userPrompt).build();
		
		return AICompletionRequestDto.builder()
		        .model(model)
		        .temperature(temperature)
		        .messages(List.of(systemMessage, userMessage))
		        .build();
	}
}
