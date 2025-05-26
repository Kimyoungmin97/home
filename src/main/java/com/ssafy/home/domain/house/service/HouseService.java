package com.ssafy.home.domain.house.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.domain.house.dao.HouseDao;
import com.ssafy.home.domain.house.dto.HouseDealResponseDto;
import com.ssafy.home.domain.house.dto.SearchHouseRequestDto;
import com.ssafy.home.domain.house.dto.SearchHouseResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HouseService {
	private final HouseDao houseDao;
	private final RedisService redisService;
	
	/**
	 * 키워드 검색
	 * @param SearchHouseRequestDto
	 * @return List
	 */
	public List<SearchHouseResponseDto> searchHousesByKeyword(SearchHouseRequestDto house){
		List<SearchHouseResponseDto> list = houseDao.searchHousesByKeyword(house);
		// Redis 에 검색 키워드 저장
		if (house.getKeyword() != null && !house.getKeyword().isBlank() && list.size()>0) {
			// redisService.saveKeyword(house.getKeyword());
		}
		return list;
	};
	
	/**
	 * 실거래 조회
	 * @param aptSeq
	 * @return List
	 */
	public List<HouseDealResponseDto> getHouseDealsByAptSeq(String aptSeq){
		return houseDao.selectHouseDealsByAptSeq(aptSeq);
	}
	
}
