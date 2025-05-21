package com.ssafy.home.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.user.model.dto.SearchHouseResponseDto;
import com.ssafy.home.user.model.service.HouseService;

import lombok.RequiredArgsConstructor;

@RestController // @RequestBody 생략 가능
@RequestMapping("/houses")
@RequiredArgsConstructor
public class HouseController {
	
	private final HouseService houseService;

	@GetMapping
	public ResponseEntity<ApiResponse<Void>> list(){
		return ResponseEntity.ok(ApiResponse.success());
	}
	
	/**
	 * [URL]
	 * 실거래가 정보 - /deals 
	 */
	
	// 검색어(아파트명) 으로 일치하는 아파트 리스트 조회 
	// GET /house?keyword=한신
	@GetMapping("/search")
	public ResponseEntity<?> searchHousesByKeyword(@RequestParam String keyword){ 
//			ApartmentSearchRequestDto requestDto = ApartmentSearchRequestDto.builder()
//					.keyword(keyword)
//					.build();
			
			List<SearchHouseResponseDto> result = houseService.searchHousesByKeyword(keyword);  
			
			// 조회 정보 없음
//			if (result==null) return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404
//					.body("조회된 데이터가 없습니다.");
//					// .build(); // 404
			
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	
}
