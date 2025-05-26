package com.ssafy.home.domain.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.domain.house.dto.HouseDealResponseDto;
import com.ssafy.home.domain.house.dto.SearchHouseRequestDto;
import com.ssafy.home.domain.house.dto.SearchHouseResponseDto;



@Mapper
public interface HouseDao {
	
	/**
	 * 키워드 검색
	 * @param SearchHouseRequestDto
	 * @return List
	 */
	List<SearchHouseResponseDto> searchHousesByKeyword(SearchHouseRequestDto house);
	
	/**
	 * 실거래 조회
	 * @param aptSeq
	 * @return List
	 */
	List<HouseDealResponseDto> selectHouseDealsByAptSeq(String aptSeq);
}
