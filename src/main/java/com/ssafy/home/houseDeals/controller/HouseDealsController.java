package com.ssafy.home.houseDeals.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.houseDeals.model.dto.ApartmentSearchRequestDto;
import com.ssafy.home.houseDeals.model.dto.ApartmentSearchResponseDto;
import com.ssafy.home.houseDeals.model.service.HouseDealsService;

import lombok.RequiredArgsConstructor;

@RestController // @RequestBody 생략 가능
@RequestMapping("/houseDeals")
@RequiredArgsConstructor
public class HouseDealsController {
	
	private final HouseDealsService houseDealsService;
	
	/**
	 * 아파트 정보 -> /apartments
	 * 거래 정보 -> /deals
	 */
	
	// 특정 지역 아파트 목록 조회 : GET, /apartments/{aptSeq}/deals
	// 특정 아파트 최근 거래 10건 : GET, /apartments/{aptSeq}/deals/recent?limit=10
	// 검색어로 특정 아파트 조회 : /apartments/search?keyword=래미안
	//			select ~ from houseinfos where apt_nm like CONCAT('%', #{keyword}, '%')
	
	// 검색어로 특정 아파트 조회 
	// GET /apartments/search?keyword=한신
	@GetMapping("/apartments/search")
	public ResponseEntity<?> searchApartmentsByName(@RequestParam String keyword
		){ 
		ApartmentSearchRequestDto requestDto = ApartmentSearchRequestDto.builder()
				.keyword(keyword)
				.build();
		
		List<ApartmentSearchResponseDto> result = houseDealsService.searchApartmentsByName(requestDto);  
		
		// 조회 정보 없음
		if (result==null) return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404
				.body("조회된 데이터가 없습니다.");
				// .build(); // 404
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
//	select *
//	from houseinfos
//	where apt_nm like concat('%', '한신', '%');
	
}
