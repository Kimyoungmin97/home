package com.ssafy.home.model.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.model.user.dto.User;

@Mapper
public interface UserDao {
	
	User select(String username); // 유저 정보 조회
	
	int update(User user);
}
