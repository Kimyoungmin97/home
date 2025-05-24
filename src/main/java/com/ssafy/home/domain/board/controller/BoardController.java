package com.ssafy.home.domain.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.page.PageRequestDto;
import com.ssafy.home.common.page.PageResponseDto;
import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.common.security.dto.CustomUserDetails;
import com.ssafy.home.domain.board.dto.BoardDetailResponseDto;
import com.ssafy.home.domain.board.dto.BoardListResponseDto;
import com.ssafy.home.domain.board.dto.InsertBoardRequestDto;
import com.ssafy.home.domain.board.dto.InsertCommentRequestDto;
import com.ssafy.home.domain.board.dto.UpdateBoardRequestDto;
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
	 * 게시글 목록 조회
	 * 사용자가 작성된 게시글 목록을 조회
	 * 
	 * GET /api/boards?page=1&size=10
	 */
	@GetMapping
	public ResponseEntity<?> getBoardList(PageRequestDto pageRequestDto){
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
	
	/**
	 * 게시글 등록
	 * 로그인한 사용자가 새 게시글을 작성
	 * 
	 * POST /api/boards
	 * 
	 * @param requestDto 게시글 등록 요청 정보
	 * @param userDetails 로그인 사용자 정보
	 * @return 등록 성공 메시지
	 */
	@PreAuthorize("hasRole('USER')")
	@PostMapping
	public ResponseEntity<?> createBoard(
			@RequestBody InsertBoardRequestDto requestDto,
			@AuthenticationPrincipal CustomUserDetails userDetails
			){
		String username = userDetails.getUsername(); // username 가져오기
		boardService.insertBoard(requestDto, username);
		
		
		return ResponseEntity.ok(ApiResponse.success("게시글이 성공적으로 등록되었습니다."));
	}
	
	/**
	 * 특정 게시글 상세 조회
	 * 댓글 목록도 함께 포함
	 * 
	 * GET /api/boards/{postId}
	 * 
	 * @param postId 게시글ID
	 * @return 게시글 상세 정보
	 */
	@GetMapping("/{postId}")
	public ResponseEntity<?> getBoardDeatil(@PathVariable long postId){
		BoardDetailResponseDto result = boardService.getBoardDetail(postId);
		return ResponseEntity.ok(ApiResponse.success(result));
	}
	
	/**
	 * 게시글 수정
	 * 로그인한 사용자가 자신의 게시글을 수정
	 * null 또는 빈 필드는 기존 데이터로 유지됨
	 * 
	 * PATCH /api/boards/{postId}
	 * 
	 * @param postId 게시글ID
	 * @param requestDto 수정 요청 데이터
	 * @param userDetails 로그인 사용자 정보
	 * @return 수정 성공 메시지
	 */
	@PreAuthorize("hasRole('USER')")
	@PatchMapping("/{postId}")
	public ResponseEntity<?> updateBoard(
			@PathVariable long postId,
			@RequestBody UpdateBoardRequestDto requestDto,
			@AuthenticationPrincipal CustomUserDetails userDetails
			){
		String username = userDetails.getUsername(); // username 가져오기
		boardService.updateBoard(postId, requestDto, username);
		return ResponseEntity.ok(ApiResponse.success("게시글이 성공적으로 수정되었습니다."));
	}

	/**
	 * 게시글 삭제
	 * 로그인한 사용자가 자신의 게시글을 삭제
	 * 댓글 먼저 삭제된 후 게시글 삭제됨
	 * 
	 * DELETE /api/boards/{postId}
	 * 
	 * @param postId 게시글ID
	 * @param userDetails 로그인 사용자 정보
	 * @return 삭제 성공 메시
	 */
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/{postId}")
	public ResponseEntity<?> deleteBoard(
			@PathVariable long postId,
			@AuthenticationPrincipal CustomUserDetails userDetails
			){
		String username = userDetails.getUsername(); // username 가져오기
		boardService.deleteBoard(username, postId);
		
		return ResponseEntity.ok(ApiResponse.success("게시글이 성공적으로 삭제되었습니다."));
	}
	
	/**
	 * 댓글 등록
	 * 로그인한 사용자가 특정 게시글에 댓글을 작성
	 * 대댓글 작성 시 parentId 를 함께 전달
	 * 
	 * POST /api/boards/{postId}/comments
	 * 
	 * @param postId 댓글이 달릴 게시글 ID
	 * @param requestDto 댓글 작성 요청 정보
	 * @param userDetails 로그인 사용자 정보
	 * @return 등록 성공 메시지
	 */
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/{postId}/comments")
	public ResponseEntity<?> createComment(
			@PathVariable long postId,
			@RequestBody InsertCommentRequestDto requestDto,
			@AuthenticationPrincipal CustomUserDetails userDetails
			){
		String username = userDetails.getUsername(); // username 가져오기
		boardService.insertComment(requestDto, username, postId);
		
		return ResponseEntity.ok(ApiResponse.success("댓글이 성공적으로 등록되었습니다."));
	}
	
	/**
	 * 댓글 목록 조회 (예정)
	 * 특정 게시글에 달린 모든 댓글을 조회합니다.
	 * 대댓글 구조가 있을 경우 계층적으로 반환할 수도 있습니다.
	 * 
	 * @param postId 대상 게시글 ID
	 * @return 댓글 목록
	 */
	// TODO: 댓글 목록 조회 API 구현 예정
	
	/**
	 * 댓글 수정, 삭제
	 */

	/**
	 * 사용자 본인 게시글 조회 (예정)
	 * 로그인한 사용자가 작성한 게시글 목록만 조회합니다.
	 * 마이페이지 등의 기능에서 활용 가능합니다.
	 * 
	 * @param userDetails 로그인 사용자 정보
	 * @return 사용자 본인 게시글 목록
	 */
	// TODO: 사용자 본인 글 조회 API 구현 예정
}
