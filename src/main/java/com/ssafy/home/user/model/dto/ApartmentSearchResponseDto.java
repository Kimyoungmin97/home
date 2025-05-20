package com.ssafy.home.user.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApartmentSearchResponseDto {
	// 아파트 이름, 코드
	// 위치 (지명) + 위도&경도
	private String aptSeq;
	private String umdNm;
	private String jibun;
	private String roadNm;
	private String roadNmBonbun;
	private String roadNmBubun;
	private String aptNm;
	private double latitude;
	private double longitude;
}
