package com.ssafy.home.houseDeals.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/house-deal")
@RequiredArgsConstructor
public class HouseDealsController {
	
	/**
	 * 아파트 정보 -> /apartments
	 * 거래 정보 -> /deals
	 */
	
	// 특정 지역 아파트 목록 조회 : GET, /apartments/{aptSeq}/deals
	// 특정 아파트 최근 거래 10건 : GET, /apartments/{aptSeq}/deals/recent?limit=10
	// 검색어로 특정 아파트 조회 : /apartments/search?keyword=래미안
	//			select ~ from houseinfos where apt_nm like CONCAT('%', #{keyword}, '%')
	
	// 검색어로 특정 아파트 조회 (GET /apartments/search?keyword=?)
	@GetMapping("/apartments/search")
	public ResponseEntity<?> searchApartmentsByName(
			@RequestParam String keyword
			){
		return null;
	}
//	select *
//	from houseinfos
//	where apt_nm like concat('%', '한신', '%');
	
}
