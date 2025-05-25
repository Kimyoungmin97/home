package com.ssafy.home.common.ai.util;

import java.util.List;

import com.ssafy.home.common.ai.dto.prompt.DealSummaryDto;
import com.ssafy.home.common.ai.dto.prompt.PriceAnalysisRequestDto;
import com.ssafy.home.common.ai.dto.prompt.PropertyEvalDealDto;
import com.ssafy.home.common.ai.dto.prompt.PropertyEvalRequestDto;
import com.ssafy.home.common.ai.prompt.PromptType;

public class PromptUtils {
	
    public static String generatePriceAnalysisPrompt(PriceAnalysisRequestDto request, PromptType type) {
        List<DealSummaryDto> historyList = request.getDealHistory();

    	// 전체 실거래가 내역 조합
    	StringBuilder sb = new StringBuilder();
    	for (DealSummaryDto h : historyList) {
            sb.append(h.getDealDate()).append(": ")
            .append(h.getDealAmount()).append("\n");
    	}
    	
        // 최종 프롬프트 조합
        return String.format(
            type.getUserPromptTemplate(),
            request.getAptName(),
            request.getLocation(),
            sb.toString().trim()
        );
    }
    
    /**
     * TODO: 아파트 정보를 통해서 매물 평가하는 프롬프트 / 아직 미완
     * @param request
     * @param type
     * @return
     */
    public static String generatePropertyEvaluationPrompt(PropertyEvalRequestDto request, PromptType type) {
        List<PropertyEvalDealDto> historyList = request.getDealHistory();
        
    	// 전체 실거래가 내역 조합
    	StringBuilder sb = new StringBuilder();
    	for (PropertyEvalDealDto h : historyList) {
    		sb.append("- 아파트 동: ").append(h.getAptDong())
            .append(" /아파트 층: ").append(h.getFloor())
            .append(" /전용 면적(㎡): ").append(h.getExclusiveUseArea())
            .append(" /거래일(㎡): ").append(h.getDealDate())
            .append(" /거래가(만 원): ").append(h.getDealAmount())
            .append("\n");
    	}
    	
        // 최종 프롬프트 조합
        return String.format(
            type.getUserPromptTemplate(),
            request.getAptName(),
            request.getLocation(),
            sb.toString().trim()
        );
    }
}
