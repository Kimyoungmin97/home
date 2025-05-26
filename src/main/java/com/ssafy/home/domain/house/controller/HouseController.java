package com.ssafy.home.domain.house.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.exception.ErrorCode;
import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.domain.house.dto.HouseDealResponseDto;
import com.ssafy.home.domain.house.dto.SearchHouseRequestDto;
import com.ssafy.home.domain.house.dto.SearchHouseResponseDto;
import com.ssafy.home.domain.house.service.HouseService;
import com.ssafy.home.domain.house.service.RedisService;

import lombok.RequiredArgsConstructor;

@RestController // @RequestBody 생략 가능
@RequiredArgsConstructor
@RequestMapping("/api/houses")
public class HouseController {
	
	private final HouseService houseService;
	private final RedisService redisService;
	
	/**
	 * 검색어로 아파트 조회
	 * 아파트명으로 검색 시, 해당 키워드를 포함한 apt_nm을 가진 매물 정보 리스트를 반환한다.
	 * 
	 * GET /api/houses/search
	 */
	@GetMapping("/search")
	public ResponseEntity<?> searchHousesByKeyword(
			@ModelAttribute SearchHouseRequestDto house){ 
		List<SearchHouseResponseDto> result;
		try {
			result = houseService.searchHousesByKeyword(house);  	
			return ResponseEntity.ok(ApiResponse.success(result));
		} catch (DataAccessException e) {
			throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
		}	
	}
	
	/**
	 * 아파트 코드로 실거래가 내역 전체 조회
	 * 아파트 코드(apt_seq)에 해당하는 전체 실거래가 내역 반환
	 * 
	 * GET /api/houses/{aptSeq}/deals
	 */
	@GetMapping("/{aptSeq}/deals")
	public ResponseEntity<ApiResponse<List>> getHouseDealsByAptSeq(@PathVariable String aptSeq){ 
		List<HouseDealResponseDto> list = new ArrayList<>();
		try {
			list = houseService.getHouseDealsByAptSeq(aptSeq);
		} catch (DataAccessException e) {
			 throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
		}
		return ResponseEntity.ok(ApiResponse.success(list));
	}
	
	@GetMapping("/search/popular")
    public ResponseEntity<ApiResponse<List<String>>> popularKeywords() {
        var redisResults = redisService.getTopKeywords(10);
        List<String> keywords = redisResults.stream()
                .map(ZSetOperations.TypedTuple::getValue)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(keywords));
    }
	
	@GetMapping("/search/recent")
	public ResponseEntity<ApiResponse<List<String>>> recentKeywords() {
	    String userId = "user123"; // 예시
	    List<String> recents = redisService.getRecentKeywords(userId);
	    return ResponseEntity.ok(ApiResponse.success(recents));
	}
	
}
