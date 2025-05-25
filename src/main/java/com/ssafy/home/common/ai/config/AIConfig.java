package com.ssafy.home.common.ai.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;

/**
 * ChatGPT 에서 사용하는 환경 구성 (Config)
 */
@Getter
@Configuration
public class AIConfig {
	
	@Value("${chatgpt.api-key}")
	private String gptKey;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public HttpHeaders httpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		// headers.set("Authorization", "Bearer " + gptKey);
		headers.setBearerAuth(gptKey);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
