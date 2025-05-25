package com.ssafy.home.domain.place.hospital.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.home.domain.place.hospital.dto.HospitalDetailResponseDto;
import com.ssafy.home.domain.place.hospital.dto.HospitalResponseDto;

@Mapper
public interface HospitalDao {
	
	List<HospitalResponseDto> selectHospitalsInRange(
			@Param("minLat") double minLat, @Param("maxLat") double maxLat,
            @Param("minLng") double minLng, @Param("maxLng") double maxLng);
	
	HospitalDetailResponseDto selectHospitalDetailById(@Param("id") int id);
}
