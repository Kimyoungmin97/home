package com.ssafy.home.common.ai.prompt;

import org.springframework.stereotype.Component;

import com.ssafy.home.domain.house.dto.PriceAnalysisRequestDto;
import com.ssafy.home.domain.house.dto.YearlyPriceDto;

/**
 * 실제 프롬프트 문장 조립 역할
 */
@Component
public class PromptTemplateProvider {
	
	public String createPrompt(PromptType type, Object requestDto) {
		return switch(type) {
			case PRICE_ANALYSIS -> createPricePrompt((PriceAnalysisRequestDto) requestDto);
		};
	}

	private String createPricePrompt(PriceAnalysisRequestDto dto) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("이 아파트는 %s에 위치한 %s입니다. ", dto.getLocation(), dto.getAptName()));
		sb.append(String.format("전용면적은 %s㎡이고, 최근 거래 가격은 다음과 같습니다:\n", dto.getArea()));
		for (YearlyPriceDto h : dto.getHistory()) {
			sb.append(h.getYear()).append(": ").append(h.getPrice()).append("억\n");
		}
		sb.append("향후 1~2년간의 거래 가격 추이를 예측해 주세요.");
		return sb.toString();
	}
}
