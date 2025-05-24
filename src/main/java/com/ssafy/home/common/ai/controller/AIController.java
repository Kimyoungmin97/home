package com.ssafy.home.common.ai.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.ai.dto.AICompletionDto;
import com.ssafy.home.common.ai.service.AIService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * GET /api/ai/modelList 	OpenAPI의 사용 가능한 모델 리스트 조회
 * GET /api/ai/model		OpenAPI에서 유효한 모델인지 체크
 * GET /api/ai/prompt		ChatGPT 모델을 이용하여 프롬프트를 호출
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;
	
    /**
     * OpenAPI의 사용 가능한 모델 리스트 조회
     * 
     * @return
     */
	@GetMapping("/modelList")
	public ResponseEntity<List<Map<String, Object>>> selectModelList(){
		List<Map<String, Object>> result = aiService.modelList();
		return ResponseEntity.ok(result);
	}
	
	/**
	 * OpenAPI에서 유효한 모델인지 체크
	 * 
	 * @param modelName
	 * @return
	 */
	@GetMapping("/model")
	public ResponseEntity<Map<String, Object>> isValidModel(@RequestParam String modelName){
		Map<String, Object> result = aiService.isValidModel(modelName);
		return ResponseEntity.ok(result);
	}
	
	/**
	 * ChatGPT 모델을 이용하여 프롬프트를 호출
	 * 
	 * @param requestDto
	 * @return
	 */
	@PostMapping("/prompt")
	public ResponseEntity<Map<String, Object>> selectPrompt(@RequestBody AICompletionDto requestDto){
		log.info("요청 DTO: {}", requestDto);
		Map<String, Object> result = aiService.prompt(requestDto);
		return ResponseEntity.ok(result);
	}
}
