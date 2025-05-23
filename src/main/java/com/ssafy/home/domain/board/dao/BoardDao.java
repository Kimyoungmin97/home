package com.ssafy.home.domain.board.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.domain.board.dto.PostDto;

@Mapper
public interface BoardDao {
	
	int insertBoard(PostDto postDto);
}
