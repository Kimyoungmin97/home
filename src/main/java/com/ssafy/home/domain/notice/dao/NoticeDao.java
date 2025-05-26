package com.ssafy.home.domain.notice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.domain.notice.dto.NoticeListResponseDto;

@Mapper
public interface NoticeDao {

	List<NoticeListResponseDto> selectAllNotices();
}
