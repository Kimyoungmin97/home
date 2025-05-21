package com.ssafy.home.user.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.user.model.dto.HouseDealResponseDto;
import com.ssafy.home.user.model.dto.SearchHouseResponseDto;



@Mapper
public interface HouseDao {
	
	List<SearchHouseResponseDto> searchHousesByKeyword(String keyword);
	
	List<HouseDealResponseDto> getHouseDealsByAptSeq(String aptSeq);
}
