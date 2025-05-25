package com.ssafy.home.common.ai.dto.prompt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DealSummaryDto {
	private String dealDate;	
	private String dealAmount;
}
