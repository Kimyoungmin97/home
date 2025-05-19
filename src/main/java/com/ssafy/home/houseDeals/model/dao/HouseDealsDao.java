package com.ssafy.home.houseDeals.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.houseDeals.model.dto.ApartmentSearchRequestDto;
import com.ssafy.home.houseDeals.model.dto.ApartmentSearchResponseDto;


@Mapper
public interface HouseDealsDao {
	
	List<ApartmentSearchResponseDto> searchApartmentsByName(ApartmentSearchRequestDto keyword);

}
