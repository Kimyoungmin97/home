package com.ssafy.home.domain.board.controller;

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
import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.common.security.dto.CustomUserDetails;
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
	 * 전체 게시글 목록 조회
	 * GET /api/boards?page=1&size=10
	 */
	@GetMapping
	public ResponseEntity<?> getBoardList(PageRequestDto requestDto){
		var pagingList = boardService.getBoardList(requestDto);
		return ResponseEntity.ok(ApiResponse.success(pagingList));
	}
	
	/**
	 * 게시글 상세 조회(댓글 포함)
	 * 비밀글은 같은 아파트 주민, 본인, 관리자만 열람 가능
	 * 
	 * GET /api/boards/{postId}
	 */
	@GetMapping("/{postId}")
	public ResponseEntity<?> getBoardDeatil(@PathVariable long postId,
											@AuthenticationPrincipal CustomUserDetails userDetails){
		var result = boardService.getBoardDetail(postId, userDetails != null ? userDetails.getUsername() : null);
		return ResponseEntity.ok(ApiResponse.success(result));
	}
	
	/**
	 * 로그인한 사용자가 게시글 등록
	 * 
	 * POST /api/boards
	 */
	@PreAuthorize("hasRole('USER')")
	@PostMapping
	public ResponseEntity<?> createBoard(@RequestBody InsertBoardRequestDto requestDto,
										 @AuthenticationPrincipal CustomUserDetails userDetails){
		boardService.insertBoard(requestDto, userDetails.getUsername());
		return ResponseEntity.ok(ApiResponse.success("게시글이 성공적으로 등록되었습니다."));
	}
	
	/**
	 * 로그인한 사용자가 자신의 게시글 수정
	 * null 또는 빈 필드는 기존 데이터로 유지됨
	 * 
	 * PATCH /api/boards/{postId}
	 */
	@PreAuthorize("hasRole('USER')")
	@PatchMapping("/{postId}")
	public ResponseEntity<?> updateBoard(@PathVariable long postId,
										 @RequestBody UpdateBoardRequestDto requestDto,
										 @AuthenticationPrincipal CustomUserDetails userDetails){
		boardService.updateBoard(postId, requestDto, userDetails.getUsername());
		return ResponseEntity.ok(ApiResponse.success("게시글이 성공적으로 수정되었습니다."));
	}

	/**
	 * 로그인한 사용자가 자신의 게시글 삭제
	 * 댓글 먼저 삭제된 후 게시글 삭제됨
	 * 
	 * DELETE /api/boards/{postId}
	 */
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/{postId}")
	public ResponseEntity<?> deleteBoard(@PathVariable long postId,
										 @AuthenticationPrincipal CustomUserDetails userDetails){
		boardService.deleteBoard(userDetails.getUsername(), postId);
		return ResponseEntity.ok(ApiResponse.success("게시글이 성공적으로 삭제되었습니다."));
	}
	
	/**
	 * 로그인한 사용자가 특정 게시글에 댓글 등록
	 * 대댓글 작성 시 parentId 를 함께 전달
	 * 
	 * POST /api/boards/{postId}/comments
	 */
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/{postId}/comments")
	public ResponseEntity<?> createComment(@PathVariable long postId,
										   @RequestBody InsertCommentRequestDto requestDto,
										   @AuthenticationPrincipal CustomUserDetails userDetails){
		boardService.insertComment(requestDto, userDetails.getUsername(), postId);
		return ResponseEntity.ok(ApiResponse.success("댓글이 성공적으로 등록되었습니다."));
	}
	
	/**
	 * 댓글 목록 조회
	 * 
	 * GET /api/boards/{postId}/comments
	 */
	@GetMapping("/{postId}/comments")
	public ResponseEntity<?> getComments(@PathVariable long postId){
		return ResponseEntity.ok(ApiResponse.success(boardService.getCommentList(postId)));
	}
	
	/**
	 * 댓글 수정
	 * 
	 * PATCH /api/boards/{postId}/comments/{commentId}
	 */
	@PreAuthorize("hasRole('USER')")
	@PatchMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<?> updateComment(@PathVariable long postId,	
								           @PathVariable long commentId,
								           @RequestBody InsertCommentRequestDto requestDto,
								           @AuthenticationPrincipal CustomUserDetails userDetails) {
	    boardService.updateComment(postId, commentId, requestDto.getContent(), userDetails.getUsername());
	    return ResponseEntity.ok(ApiResponse.success("댓글이 수정되었습니다."));
	}
	
	/**
	 * 댓글 삭제
	 * 
	 * DELETE /api/boards/{postId}/comments/{commentId}
	 */
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable long postId,
								           @PathVariable long commentId,
								           @AuthenticationPrincipal CustomUserDetails userDetails) {
	    boardService.deleteComment(postId, commentId, userDetails.getUsername());
	    return ResponseEntity.ok(ApiResponse.success("댓글이 삭제되었습니다."));
	}

	/**
	 * 사용자 본인 게시글 목록 조회
	 * GET /api/boards/my
	 */
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/my")
	public ResponseEntity<?> getMyBoards(@AuthenticationPrincipal CustomUserDetails userDetails) {
	    return ResponseEntity.ok(ApiResponse.success(boardService.getBoardsByUsername(userDetails.getUsername())));
	}
}
