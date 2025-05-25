package com.ssafy.home.common.ai.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.ai.dto.prompt.PriceAnalysisRequestDto;
import com.ssafy.home.common.ai.prompt.PromptType;
import com.ssafy.home.common.ai.service.AIPromptService;
import com.ssafy.home.common.ai.util.PromptUtils;
import com.ssafy.home.common.response.ApiResponse;

import lombok.RequiredArgsConstructor;

/**
 * 분석 요청 API
 * 요청 JSON 받으면 → 서비스 호출 → 응답 return
 * @PostMapping("/api/ai/analyze/{type}")
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AIPromptController {
	
	private final AIPromptService aiPromptService;
	
	@PostMapping("/analyze/price")
	public ResponseEntity<?> postMethodName(@RequestBody PriceAnalysisRequestDto requestDto) {
		PromptType type = PromptType.PRICE_ANALYSIS;
		
		String userPrompt = PromptUtils.generatePriceAnalysisPrompt(requestDto, type);
		
		Map<String, Object> result = aiPromptService.analyze(type.getSystemPrompt(), userPrompt);
		return ResponseEntity.ok(ApiResponse.success(result));
	}
	
}
