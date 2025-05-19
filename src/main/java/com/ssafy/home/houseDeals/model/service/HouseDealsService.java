package com.ssafy.home.houseDeals.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.houseDeals.model.dao.HouseDealsDao;
import com.ssafy.home.houseDeals.model.dto.ApartmentSearchRequestDto;
import com.ssafy.home.houseDeals.model.dto.ApartmentSearchResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HouseDealsService {
	private final HouseDealsDao houseDealsDao;
	
	public List<ApartmentSearchResponseDto> searchApartmentsByName(ApartmentSearchRequestDto keyword){
		return houseDealsDao.searchApartmentsByName(keyword);
	};
	
}
