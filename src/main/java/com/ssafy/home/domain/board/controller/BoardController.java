package com.ssafy.home.domain.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.page.PageRequestDto;
import com.ssafy.home.common.page.PageResponseDto;
import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.common.security.dto.CustomUserDetails;
import com.ssafy.home.domain.board.dto.BoardListResponseDto;
import com.ssafy.home.domain.board.dto.InsertBoardRequestDto;
import com.ssafy.home.domain.board.service.BoardService;

import lombok.RequiredArgsConstructor;

/**
 * 동네 커뮤니티 게시판
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
	
	private final BoardService boardService;
	
	/**
	 * 게시판 글 작성
	 * POST /api/boards
	 */
	@PreAuthorize("hasRole('USER')")
	@PostMapping
	public ResponseEntity<?> insertBoard(
			@RequestBody InsertBoardRequestDto requestDto,
			@AuthenticationPrincipal CustomUserDetails userDetails
			){
		String username = userDetails.getUsername(); // username 가져오기
		boardService.insertBoard(requestDto, username);
		
		
		return ResponseEntity.ok(ApiResponse.success("게시글 등록 완료"));
	}
	
	/**
	 * 게시판 글 내역 조회
	 * 페이징 기능
	 * GET /api/boards?page=1&size=10
	 */
	@GetMapping
	public ResponseEntity<?> getBoard(PageRequestDto pageRequestDto){
		List<BoardListResponseDto> fullList = boardService.getBoardList(pageRequestDto);
		long totalCount = boardService.getTotalCount(); // 전체 글 수
		
		PageResponseDto<BoardListResponseDto> pagingList = PageResponseDto
				.<BoardListResponseDto>withAll()
				.dtoList(fullList)
				.pageRequestDTO(pageRequestDto)
				.totalCount(totalCount)
				.build();
				
		return ResponseEntity.ok(ApiResponse.success(pagingList));
	}
}
