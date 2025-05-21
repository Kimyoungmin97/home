package com.ssafy.home.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.user.model.dto.HouseDealResponseDto;
import com.ssafy.home.user.model.dto.SearchHouseResponseDto;
import com.ssafy.home.user.model.service.HouseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController // @RequestBody 생략 가능
@RequiredArgsConstructor
@RequestMapping("/api/v1/houses")
@Tag(name = "HouseController", description = "실거래가 관련 기능 처리")
public class HouseController {
	
	private final HouseService houseService;

	@GetMapping
	public ResponseEntity<ApiResponse<Void>> list(){
		return ResponseEntity.ok(ApiResponse.success());
	}
	
	
	// 검색어(아파트명) 으로 일치하는 아파트 리스트 조회 
	// GET /houses/search?keyword=한신
	@GetMapping("/search")
	@Operation(summary = "검색어로 아파트 조회", description = "아파트명으로 검색 시, 해당 키워드를 포함한 apt_seq을 가진 매물 정보 리스트를 반환한다.")
	public ResponseEntity<?> searchHousesByKeyword(@RequestParam String keyword){ 
		List<SearchHouseResponseDto> result = houseService.searchHousesByKeyword(keyword);  	
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	// GET /houses/{aptSeq}/deals
	@GetMapping("/{aptSeq}/deals")
	public ResponseEntity<?> getHouseDealsByAptSeq(@PathVariable String aptSeq){ 
		List<HouseDealResponseDto> result = houseService.getHouseDealsByAptSeq(aptSeq);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	// /api/houses/{houseId}/deals?sort=price_desc
	// /api/houses/{houseId}/deals?year=2024&month=3
	
//	ApartmentSearchRequestDto requestDto = ApartmentSearchRequestDto.builder()
//	.keyword(keyword)
//	.build();
	
	// 조회 정보 없음
//	if (result==null) return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404
//			.body("조회된 데이터가 없습니다.");
//			// .build(); // 404
	
}
