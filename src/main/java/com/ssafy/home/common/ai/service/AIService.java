package com.ssafy.home.common.ai.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.home.common.ai.dto.openai.AICompletionRequestDto;

/**
 * ChatGPT Service 인터페이스
 */
@Service
public interface AIService {
	
	List<Map<String, Object>> modelList();
	
	Map<String, Object> prompt(AICompletionRequestDto requestDto);
	
	Map<String, Object> isValidModel(String modelName);
}
