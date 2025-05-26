package com.ssafy.home.domain.house.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SearchHouseResponseDto {
	// 아파트 이름, 코드
	// 위치 (지명) + 위도&경도
	private String aptSeq;
	private String aptNm;
	private String address;
	private String price;
	private double latitude;
	private double longitude;
	private int avgPriceAll;
	private int avgPriceNearPeak;
}
