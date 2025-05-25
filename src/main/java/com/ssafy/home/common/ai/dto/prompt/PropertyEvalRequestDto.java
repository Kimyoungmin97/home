package com.ssafy.home.common.ai.dto.prompt;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PropertyEvalRequestDto {
	private String aptName;	// 아파트명
	private String location;	// 지역 
	private List<PropertyEvalDealDto> dealHistory; // 거래 내역
}
