package com.ssafy.home.user.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HouseInfos {
	private String aptSeq;
	private String sggCd;
	private String umdCd;
	private String umdNm;
	private String jibun;
	private String roadNmSggCd;
	private String roadNm;
	private String roadNmBonbun;
	private String roadNmBubun;
	private String aptNm;
	private int buildYear;
	private String latitude;
	private String longitude;
	
	@Builder.Default
	private List<HouseDeals> houseDealList = new ArrayList<>();
	
}
