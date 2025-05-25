package com.ssafy.home.domain.place.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.domain.place.hospital.dto.HospitalDetailResponseDto;
import com.ssafy.home.domain.place.hospital.dto.HospitalResponseDto;
import com.ssafy.home.domain.place.hospital.service.HospitalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/places/hospitals")
public class HospitalController {
	
	private final HospitalService hospitalService;
	
	@Value("${hospital.search.default-radius-km}")
	private double defaultRadiusKm;
	
	/**
	 * 좌표 기반 반경 내 병원 검색 (요약 정보) (반경 n Km 내외)
	 * 
	 * GET /api/places/hospitals/search?latitude=127.0&longitude=37.5&radius=2
	 */
	@GetMapping("/search")
	public ResponseEntity<?> getHospitalByDistance(@RequestParam Double latitude,
												   @RequestParam Double longitude,
												   @RequestParam(required = false) Double radius){
		radius = (radius!=null) ? radius:defaultRadiusKm;
		List<HospitalResponseDto> result = hospitalService.getHospitalsWithinRadius(latitude, longitude, radius);
		return ResponseEntity.ok(ApiResponse.success(result));
	}
	
	/**
	 * 병원 상세정보 조회
	 * GET /api/places/hospitals/{id}
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getHospitalDetail(@PathVariable int id){
		HospitalDetailResponseDto result = hospitalService.getHospitalDetailById(id);
		return ResponseEntity.ok(ApiResponse.success(result));
	}
}
