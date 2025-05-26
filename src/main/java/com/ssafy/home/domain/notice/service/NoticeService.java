package com.ssafy.home.domain.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.domain.notice.dao.NoticeDao;
import com.ssafy.home.domain.notice.dto.NoticeListResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	
	private final NoticeDao noticeDao;
	
	public List<NoticeListResponseDto> getNoticeList(){
		return noticeDao.selectAllNotices();
	}
	
}
