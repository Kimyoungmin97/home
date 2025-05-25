package com.ssafy.home.domain.board.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.exception.ErrorCode;
import com.ssafy.home.common.page.PageRequestDto;
import com.ssafy.home.common.page.PageResponseDto;
import com.ssafy.home.domain.board.dao.BoardDao;
import com.ssafy.home.domain.board.dto.BoardDetailResponseDto;
import com.ssafy.home.domain.board.dto.BoardListResponseDto;
import com.ssafy.home.domain.board.dto.CommentDto;
import com.ssafy.home.domain.board.dto.CommentResponseDto;
import com.ssafy.home.domain.board.dto.InsertBoardRequestDto;
import com.ssafy.home.domain.board.dto.InsertCommentRequestDto;
import com.ssafy.home.domain.board.dto.PostDto;
import com.ssafy.home.domain.board.dto.UpdateBoardRequestDto;
import com.ssafy.home.domain.user.dao.UserDao;
import com.ssafy.home.domain.user.dto.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardDao boardDao;
	private final UserDao userDao;
	
	/**
	 * username으로 userId 조회
	 * 존재하지 않으면 USER_NOT_FOUND 예외 발생
	 */
	private int getUserId(String username) {
		return Optional.ofNullable(userDao.selectUserIdByUsername(username))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
	
	/**
	 * 게시글 목록 조회 (페이징)
	 * @param pageRequestDto 페이지 요청정보
	 */
	public PageResponseDto<BoardListResponseDto> getBoardList(PageRequestDto requestDto) {
		List<BoardListResponseDto> list = boardDao.selectBoardsByPage(requestDto.getOffset(), requestDto.getSize());
		long count = boardDao.selectBoardCount(); // 전체 글 수
		
		return PageResponseDto.<BoardListResponseDto>withAll()
				.dtoList(list).pageRequestDTO(requestDto)
				.totalCount(count).build();
	}
	
	/**
	 * 게시글 상세 조회
	 */
	public BoardDetailResponseDto getBoardDetail(long postId, String username) {
		BoardDetailResponseDto post = boardDao.selectBoardDetail(postId);
		if (post == null) throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
		
		// 비밀글 관련 처리
		if (Boolean.TRUE.equals(post.isSecret())) {
			if (username == null) throw new CustomException(ErrorCode.BOARD_ACCESS_DENIED);
			User user = userDao.selectAllByUsername(username);
			boolean authorized = Objects.equals(user.getUserId(), post.getUserId()) ||
								 Objects.equals(user.getAptSeq(), post.getAptSeq()) ||
								 Objects.equals("ROLE_ADMIN", user.getRole());
			
	        if (!authorized) throw new CustomException(ErrorCode.BOARD_ACCESS_DENIED);
		}
		return post;
	}
	
	/**
	 * 게시글 등록
	 */
	public int insertBoard(InsertBoardRequestDto requestDto, String username) {
		int userId = getUserId(username);
		
		PostDto post = PostDto.builder()
				.aptSeq(requestDto.getAptSeq()).userId(userId)
				.title(requestDto.getTitle()).content(requestDto.getContent())
				.isSecret(requestDto.getSecret())
				.build();
		return boardDao.insertBoard(post);
	};

	
	/**
	 * 게시글 수정
	 */
	public void updateBoard(long postId, UpdateBoardRequestDto requestDto, String username) {
		
		PostDto post = boardDao.selectPostById(postId);
		if (post == null) throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
		
		int userId = getUserId(username);
		if (Integer.compare(post.getUserId(), userId) != 0) throw new CustomException(ErrorCode.BOARD_UPDATE_FORBIDDEN);

		// https://dev-coco.tistory.com/178
		Optional.ofNullable(requestDto.getTitle()).filter(s -> !s.isBlank()).ifPresent(post::setTitle);
		Optional.ofNullable(requestDto.getContent()).filter(s -> !s.isBlank()).ifPresent(post::setContent);
		Optional.ofNullable(requestDto.getIsSecret()).ifPresent(post::setSecret);
		
		boardDao.updateBoard(post);
	}
	
	/**
	 * 게시글 삭제
	 */
	@Transactional
	public void deleteBoard(String username, long postId) {
		PostDto post = boardDao.selectPostById(postId);
		int userId = getUserId(username);
		if (post==null || Integer.compare(post.getUserId(), userId) != 0) {
			throw new CustomException(ErrorCode.BOARD_DELETE_FORBIDDEN);
		}

		boardDao.deleteAllCommentsByPostId(postId);
		boardDao.deleteBoardByPostId(postId);
	}
	
	/**
	 * 특정 게시판에 댓글 또는 대댓글 등록
	 */
	public int insertComment(InsertCommentRequestDto requestDto, String username, long postId) {
		int userId = getUserId(username);	
		CommentDto.CommentDtoBuilder builder = CommentDto.builder()
				.postId(postId).userId(userId).content(requestDto.getContent());
		
		Optional.ofNullable(requestDto.getParentId()).ifPresent(builder::parentId);
		return boardDao.insertComment(builder.build());
	}
	
	/**
	 * 댓글 목록 조회
	 */
	public List<CommentResponseDto> getCommentList(long postId){
		return boardDao.selectCommentsByPostId(postId);
	}
	
	/**
	 * 댓글 수정
	 */
	public void updateComment(long postId, long commentId, String content, String username) {
		int userId = getUserId(username);
		CommentResponseDto comment = boardDao.selectCommentById(postId, commentId);
		
		if (comment == null) throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
		if (Integer.compare(comment.getUserId(), userId) != 0) throw new CustomException(ErrorCode.BOARD_UPDATE_FORBIDDEN);

		if (content!=null && !content.isBlank()) {
			comment.setContent(content);	
			boardDao.updateComment(postId, commentId, content, userId);
		}
	}
	
	/**
	 * 댓글 삭제
	 */
	public void deleteComment(long postId, long commentId, String username) {
		int userId = getUserId(username);
		CommentResponseDto comment = boardDao.selectCommentById(postId, commentId);
		
		if (comment == null) throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
		if (Integer.compare(comment.getUserId(), userId) != 0) throw new CustomException(ErrorCode.BOARD_DELETE_FORBIDDEN);

		boardDao.deleteComment(postId, commentId, userId);
	}
	
	/**
	 * 사용자 본인 게시글 조회
	 */
	public List<BoardListResponseDto> getBoardsByUsername(String username){
		int userId = getUserId(username);
		return boardDao.findBoardsByUserId(userId);
	}
	
}
