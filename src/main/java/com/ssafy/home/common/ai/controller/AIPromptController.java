package com.ssafy.home.common.ai.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.ai.prompt.PromptType;
import com.ssafy.home.common.ai.service.AIPromptService;
import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.domain.house.dto.PriceAnalysisRequestDto;
import com.ssafy.home.domain.house.dto.YearlyPriceDto;

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
		
		// 거래 내역 문자열 조합
		StringBuilder sb = new StringBuilder();
		for (YearlyPriceDto h : requestDto.getHistory()) {
			sb.append(h.getYear()).append(": ").append(h.getPrice()).append("억\n");
		}
		String history = sb.toString();
		
		// 프롬프트 완성
		String userPrompt = String.format(
				type.getUserPromptTemplate(),
				requestDto.getLocation(),
				requestDto.getAptName(),
				requestDto.getArea(),
				history
		);
		
		Map<String, Object> result = aiPromptService.analyze(type.getSystemPrompt(), userPrompt);
		return ResponseEntity.ok(ApiResponse.success(result));
	}
	
}
