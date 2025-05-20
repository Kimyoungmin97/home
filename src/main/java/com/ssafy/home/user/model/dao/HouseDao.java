package com.ssafy.home.user.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.houseDeals.model.dto.ApartmentSearchRequestDto;
import com.ssafy.home.houseDeals.model.dto.ApartmentSearchResponseDto;


@Mapper
public interface HouseDao {
	
	List<ApartmentSearchResponseDto> searchApartmentsByName(ApartmentSearchRequestDto keyword);

}
