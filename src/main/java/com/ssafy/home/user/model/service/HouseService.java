package com.ssafy.home.user.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.user.model.dao.HouseDao;
import com.ssafy.home.user.model.dto.HouseDealResponseDto;
import com.ssafy.home.user.model.dto.SearchHouseResponseDto;

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
