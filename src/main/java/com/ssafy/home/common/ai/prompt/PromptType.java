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
		"""
		당신은 대한민국의 부동산 전문가 AI입니다.
		실거래가 변동 추이를 분석하고, 연도별 변화량과 평균 상승률(%) 등을 수치적으로 언급하며 향후 5년 간의 거래 가격을 간결하게 예측합니다.
		또한 사용자에게 해당 매물을 구매해도 좋을지 타당한 분석 결과를 기반으로 추천해야 합니다.
		분석 결과는 최대 100자 이내로 요약해주세요.
		""",
		"""
		[아파트 정보]
		- 단지명: %s
		- 지역: %s

		[최근 5년간 실거래 내역] (단위: 만 원):
		%s
		
		위 데이터를 기반으로 다음 항목을 포함해 분석해주세요.
		- 연도별 가격 추이 요약
		- 연평균 상승률 또는 하락률
		- 향후 5년 가격 예측 (예상 범위 포함)
		- 이 아파트의 매수 추천 여부의 간단한 이유
		"""
	);
	
	private final String systemPrompt;
	private final String userPromptTemplate;

}
