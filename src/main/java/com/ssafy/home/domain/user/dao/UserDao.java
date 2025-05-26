package com.ssafy.home.domain.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.domain.user.dto.User;

@Mapper
public interface UserDao {
	
	User selectAllByUsername(String username); // 유저 정보 조회
	
	int update(User user);
	
	int selectUserIdByUsername(String username); // username 조회
	
	int insert(User user);

	int delete(int userId);
	
	int updateProfile(String username, byte[] profileImage);
}
