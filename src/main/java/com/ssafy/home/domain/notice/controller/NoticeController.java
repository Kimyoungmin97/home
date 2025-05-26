package com.ssafy.home.domain.notice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.domain.notice.dto.NoticeListResponseDto;
import com.ssafy.home.domain.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {
	
	private final NoticeService noticeService;
	
	/**
	 * 전체 공지글 조회
	 * GET /api/notices
	 */
	@GetMapping
	public ResponseEntity<?> getNoticeList(){
		List<NoticeListResponseDto> result = noticeService.getNoticeList();
		return ResponseEntity.ok(ApiResponse.success(result));
	}
	
}
