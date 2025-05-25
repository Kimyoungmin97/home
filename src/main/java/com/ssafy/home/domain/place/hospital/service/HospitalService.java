package com.ssafy.home.domain.place.hospital.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.domain.place.hospital.dao.HospitalDao;
import com.ssafy.home.domain.place.hospital.dto.HospitalDetailResponseDto;
import com.ssafy.home.domain.place.hospital.dto.HospitalResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class HospitalService {
	
	private final HospitalDao hospitalDao;
	
	public List<HospitalResponseDto> getHospitalsWithinRadius(double lat, double lng, double radius){
		double degLat = radius / 111.0;
	    double degLng = radius / (111.0*Math.cos(Math.toRadians(lat)));
	    // 1도 = 111km
		
		double minLat = lat - degLat;
		double maxLat = lat + degLat;
		double minLng = lng - degLng;
		double maxLng = lng + degLng;
		
		log.debug("minLat: {}, maxLat:{}, minLng: {}, maxLng: {}", minLat, maxLat, minLng, maxLng);
		
		List<HospitalResponseDto> all = hospitalDao.selectHospitalsInRange(minLat, maxLat, minLng, maxLng);
		List<HospitalResponseDto> filteredResult = new ArrayList<>();
		
		
		for (HospitalResponseDto h : all) {
			double dist = harversine(lat, lng, h.getLatitude(), h.getLongitude());
			if (dist <= radius) filteredResult.add(h);
		}
		
		log.debug("모든 Row: {}, harversine 필터 처리 후: {}", all.size(), filteredResult.size());
		
		return filteredResult;
	}
	
	public HospitalDetailResponseDto getHospitalDetailById(int id) {
		return hospitalDao.selectHospitalDetailById(id);
	}
	
	private double harversine(double lat1, double lng1, double lat2, double lng2) {
		final int R = 6371; // 지구 반지름 (km)
	    double dLat = Math.toRadians(lat2 - lat1);
	    double dLng = Math.toRadians(lng2 - lng1);
	    
	    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) // ✅ 괄호 고침
	            * Math.sin(dLng / 2) * Math.sin(dLng / 2);                        // ✅ 곱셈 연결

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    return R * c;
	}

}
