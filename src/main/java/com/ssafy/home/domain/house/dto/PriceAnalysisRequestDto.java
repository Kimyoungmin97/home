package com.ssafy.home.domain.house.dto;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PriceAnalysisRequestDto {
	private String aptName;
	private String location;
	private String area;
	private List<YearlyPriceDto> history;
}
