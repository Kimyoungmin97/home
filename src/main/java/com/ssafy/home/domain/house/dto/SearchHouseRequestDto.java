package com.ssafy.home.domain.house.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder // Builder 패턴
public class SearchHouseRequestDto {
	private String keyword;		// 검색어
	private String lastAptSeq;  // 마지막 조회한 apt_seq (ex: "11110-2109")
	private int size = 10;      // 한 번에 불러올 개수
}
