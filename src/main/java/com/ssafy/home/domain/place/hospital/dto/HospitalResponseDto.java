package com.ssafy.home.domain.place.hospital.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jsonwebtoken.lang.Arrays;
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
public class HospitalResponseDto {
	private int id;					// 기본 PK
	private String encryptedCode;	// 암호화된 요양기관 식별코드
	private String name;			// 요양기관명
	private String address;			// 주소
	private String phone;			// 전화번호
	private String orgTypeName;		// 종별코드명
	private double latitude;		// 위도
	private double longitude;		// 경도

	private List<String> departments;	// 진료과목명 리스트
	
	@JsonIgnore
	private String departmentsRaw;
	
	public List<String> getDepartments(){
		return departmentsRaw != null ? Arrays.asList(departmentsRaw.split(",")) : List.of();
	}
}
