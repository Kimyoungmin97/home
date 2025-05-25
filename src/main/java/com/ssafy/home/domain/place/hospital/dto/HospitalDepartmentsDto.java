package com.ssafy.home.domain.place.hospital.dto;

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
public class HospitalDepartmentsDto {
	private String subjectName;			// 진료과목명
	private String subjectCode;			// 진료과목코드
	private int specialistCount;		// 과목별 전문의수
	private int selectiveDoctorCount;	// 선택 진료 의사수 
}
