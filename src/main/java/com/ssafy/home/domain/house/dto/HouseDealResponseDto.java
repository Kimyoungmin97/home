package com.ssafy.home.domain.house.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HouseDealResponseDto {
	private int no;
	private String date;		// 거래일
	private int floor;			// 아파트층
	private String excluUseAr;	// 아파트면적
	private int dealAmount;		// 거래가격
	private String price;		// 거래가격
}
