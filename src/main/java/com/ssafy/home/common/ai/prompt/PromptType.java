package com.ssafy.home.common.ai.prompt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 프롬프트 유형 정의 및 설명
 */
@Getter
@RequiredArgsConstructor
public enum PromptType {
	
	PRICE_ANALYSIS(
		"당신은 대한민국의 부동산 전문가 AI입니다. 거래 내역을 분석하고 향후 가격을 예측합니다.",
		"이 아파트는 %s에 위치한 %s입니다. 전용면적은 %s㎡이고, 최근 거래 가격은 다음과 같습니다:\n%s향후 1~2년간의 거래 가격 추이를 예측해 주세요."
	);
	
	private final String systemPrompt;
	private final String userPromptTemplate;

}
