package com.ssafy.home.common.ai.dto.prompt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PropertyEvalDealDto {
	private String dealDate;	// deal_year + deal_month + deal_day
	private String dealAmount;
	
	private String aptDong;
	private Integer floor;
	
	private Double exclusiveUseArea;	// 전용면적 (㎡ 단위)
}
