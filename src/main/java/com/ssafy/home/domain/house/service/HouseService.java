package com.ssafy.home.domain.house.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.domain.house.dao.HouseDao;
import com.ssafy.home.domain.house.dto.HouseDealResponseDto;
import com.ssafy.home.domain.house.dto.SearchHouseResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HouseService {
	private final HouseDao houseDao;
	
	public List<SearchHouseResponseDto> searchHousesByKeyword(String keyword){
		return houseDao.searchHousesByKeyword(keyword);
	};
	
	public List<HouseDealResponseDto> getHouseDealsByAptSeq(String aptSeq){
		return houseDao.getHouseDealsByAptSeq(aptSeq);
	}
}
