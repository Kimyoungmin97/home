package com.ssafy.home.user.model.dto;

// import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
// @AllArgsConstructor
@ToString
@Builder // Builder 패턴
public class SearchHouseRequestDto {
	private String keyword;
}
