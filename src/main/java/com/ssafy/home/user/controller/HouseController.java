package com.ssafy.home.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.exception.ErrorCode;
import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.user.model.dto.HouseDealResponseDto;
import com.ssafy.home.user.model.dto.SearchHouseResponseDto;
import com.ssafy.home.user.model.service.HouseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController // @RequestBody 생략 가능
@RequiredArgsConstructor
@RequestMapping("/api/houses")
@Tag(name = "HouseController", description = "실거래가 관련 기능 처리")
public class HouseController implements ControllerHelper {
	
	private final HouseService houseService;

	// GET /api/v1/houses/search?keyword=한신
	// @Secured("ROLE_USER")
	// @PreAuthorize("hasRole('USER')")
	@GetMapping("/search")
	@Operation(summary = "검색어로 아파트 조회", description = "아파트명으로 검색 시, 해당 키워드를 포함한 apt_nm을 가진 매물 정보 리스트를 반환한다.")
	public ResponseEntity<?> searchHousesByKeyword(@RequestParam String keyword){ 
		try {
			List<SearchHouseResponseDto> result = houseService.searchHousesByKeyword(keyword);  	
			return handleSuccess(result);
		} catch (DataAccessException e) {
			return handleFail(e);
		}
	}
	
	// GET /api/v1/houses/{aptSeq}/deals
	@GetMapping("/{aptSeq}/deals")
	@Operation(summary = "아파트 코드로 실거래가 내역 전체 조회", description = "아파트 코드(apt_seq)에 해당하는 전체 실거래가 내역 반환")
	public ResponseEntity<ApiResponse<List>> getHouseDealsByAptSeq(@PathVariable String aptSeq){ 
		List<HouseDealResponseDto> result = new ArrayList<>();
		try {
			result = houseService.getHouseDealsByAptSeq(aptSeq);
//			return handleSuccess(result);
		} catch (DataAccessException e) {
			 throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
//			return ResponseEntity
//                    .status(500)
//                    .body(ApiResponse.fail(500, "서버 내부 오류가 발생했습니다.",result));
		}
		return ResponseEntity.ok(ApiResponse.success(result));
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
