package com.ssafy.home.user.model.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HouseDealResponseDto {
	private int no; 
	private String aptSeq; // 아파트코드
    private String aptName; // 아파트명 
    private String aptDong; // 아파트동
    private int floor;	// 아파트층
    private double exclusiveUseArea; // 아파트면적

    private String dealAmount;  // 거래가격
    private LocalDate dealDate; // 거래년도

//    private Double avgDealAmount; // 평균거래가
}
