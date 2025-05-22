package com.ssafy.home.domain.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.domain.house.dto.HouseDealResponseDto;
import com.ssafy.home.domain.house.dto.SearchHouseResponseDto;



@Mapper
public interface HouseDao {
	
	List<SearchHouseResponseDto> searchHousesByKeyword(String keyword);
	
	List<HouseDealResponseDto> getHouseDealsByAptSeq(String aptSeq);
}
