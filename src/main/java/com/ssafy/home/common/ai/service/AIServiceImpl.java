package com.ssafy.home.common.ai.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.home.common.ai.dto.AICompletionDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ChatGPT Service 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {
	
    // private final AIConfig aiConfig;
    private final HttpHeaders httpHeaders;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${chatgpt.url.model}")
    private String modelUrl;
    
    @Value("${chatgpt.url.model-list}")
    private String modelListUrl;

    @Value("${chatgpt.url.prompt}")
    private String promptUrl;

    /**
     * 사용 가능한 모델 리스트를 조회하는 비즈니스 로직
     *
     * @return 
     */
    @Override
    public List<Map<String, Object>> modelList() {
    	try {
    		// [STEP1] 통신을 위한 RestTemplate을 구성
            ResponseEntity<String> response = restTemplate.exchange(
            		modelUrl, HttpMethod.GET, 
                    new HttpEntity<>(httpHeaders), String.class
            );
            
            // [STEP2] Jackson을 기반으로 응답값을 Map 으로 파싱
            Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
            
            // [STEP4] 응답 값("data")을 추출
            List<Map<String, Object>> resultList = objectMapper.convertValue(
            		responseMap.get("data"), 
            		new TypeReference<List<Map<String, Object>>>() {});
            
            return resultList;
    	} catch (Exception e) {
    		log.error("[!] 모델 리스트 조회 실패", e);
    		throw new RuntimeException("모델 리스트 조회 중 오류 발생", e);
    	}
    }

    @Override
    public Map<String, Object> isValidModel(String modelName) {
    	
    	log.debug("[+] 모델이 유효한지 조회합니다. 모델 : " + modelName);
    	try {
    		// [STEP2] 통신을 위한 RestTemplate을 구성합니다.
    		ResponseEntity<String> response = restTemplate.exchange(
            		modelListUrl + "/" + modelName, 
            		HttpMethod.GET, new HttpEntity<>(httpHeaders), 
            		String.class
    		);
    		
    		return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (Exception e) {
            log.error("[!] 모델 유효성 검사 실패", e);
            throw new RuntimeException("모델 유효성 검사 중 오류 발생", e);
        }
    }

    /**
     * 신규 모델에 대한 프롬프트
     *
     * @param chatCompletionDto {}
     * @return chatCompletionDto
     */
    @Override
    public Map<String, Object> prompt(AICompletionDto completionDto) {
    	try {
            HttpEntity<AICompletionDto> request = new HttpEntity<>(completionDto, httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(
                promptUrl, HttpMethod.POST, request, String.class
            );

            return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (Exception e) {
            log.error("[!] GPT 프롬프트 요청 실패", e);
            throw new RuntimeException("GPT 프롬프트 요청 중 오류 발생", e);
        }
    }

}
