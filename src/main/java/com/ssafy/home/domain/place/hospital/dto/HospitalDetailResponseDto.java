package com.ssafy.home.domain.place.hospital.dto;

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
public class HospitalDetailResponseDto {
	private int id;						// 기본 PK
    private String encryptedCode;		// 암호화된 요양기관 식별코드
    private String name;				// 요양기관명
    private String sidoCode;			// 시도 코드
    private String sidoName;			// 시도명
    private String sigunguCode;			// 시군구 코드
    private String sigunguName;			// 시군구명
    private String eupmyeondong;		// 읍면동
    private String zipcode;				// 우편번호
    private String address;				// 주소
    private Double longitude;			// 경도(X)
    private Double latitude;			// 위도(Y)
    private String phone;				// 전화번호
    private String homepage;			// 병원 홈페이지
    private String orgTypeCode;			// 종별코드
    private String orgTypeName;			// 종별코드명
    private Integer doctorTotal;		// 총의사수
    private Integer doctorGeneral;		// 의과일반의 인원수
    private Integer doctorIntern;		// 의과인턴 인원수
    private Integer doctorResident;		// 의과레지던트 인원수
    private Integer doctorSpecialist;	// 의과전문의 인원수
    private Integer midwifeCount;		// 조산사 인원수
    private Integer bedHighclass;		// 일반입원실상급병상수
    private Integer bedGeneral;			// 일반입원실일반병상수
    private Integer bedAdultIcu;		// 성인중환자병상수
    private Integer bedChildIcu;		// 소아중환자병상수
    private Integer bedNewbornIcu;		// 신생아중환자병상수
    private Integer bedBirth;			// 분만실병상수
    private Integer bedSurgery;			// 수술실병상수
    private Integer bedEmergency;		// 응급실병상수
    
    private List<HospitalDepartmentsDto> departments;
}

