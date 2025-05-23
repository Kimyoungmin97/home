package com.ssafy.home.domain.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.exception.ErrorCode;
import com.ssafy.home.common.page.PageRequestDto;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardDao boardDao;
	private final UserDao userDao;
	
	public int insertBoard(InsertBoardRequestDto requestDto, String username) {
		// 1. users 테이블에서 user_id 조회해옴
		int userId = userDao.selectUserId(username);
		
		// 2. PostDto 객체 만들어서 insert 
		PostDto post = PostDto.builder()
				.aptSeq(requestDto.getAptSeq())
				.userId(userId)
				.title(requestDto.getTitle())
				.content(requestDto.getContent())
				.isSecret(requestDto.getSecret())
				.build();
		
		return boardDao.insertBoard(post);
	};
	
	public List<BoardListResponseDto> getBoardList(PageRequestDto pageRequestDto) {
		return boardDao.selectBoardList(pageRequestDto.getOffset(), pageRequestDto.getSize());
	}
	
	public long getTotalCount() {
		return boardDao.selectBoardCount();
	}
	
	public BoardDetailResponseDto getBoardDetail(long postId) {
		BoardDetailResponseDto post = boardDao.selectBoardDetail(postId);
		if (post==null) throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
//		
//		List<CommentResponseDto> comments = boardDao.selectCommentsByPostId(postId);
//		post.setComments(comments);
		
		return post;
	}
	
	/**
	 * 댓글 등록 (작성)
	 */
	public int insertComment(InsertCommentRequestDto requestDto, String username, long postId) {
		// 1. users 테이블에서 user_id 조회해옴
		int userId = userDao.selectUserId(username);
				
		CommentDto.CommentDtoBuilder builder = CommentDto.builder()
				.postId(postId)
				.userId(userId)	
				.content(requestDto.getContent());
		
		if (requestDto.getParentId()!=null) builder.parentId(requestDto.getParentId());
		
		CommentDto comment = builder.build();
		
		return boardDao.insertComment(comment);
	}
	
	/**
	 * 게시글 수정
	 */
	public void updateBoard(long postId, UpdateBoardRequestDto requestDto, String username) {
		
		PostDto post = boardDao.selectPostById(postId);
		if (post == null) {
			throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
		}
		
		int userId = userDao.selectUserId(username);
		if (post.getUserId() != userId) {
			throw new CustomException(ErrorCode.BOARD_UPDATE_FORBIDDEN);
		}
		
		// 수정
		
		// https://dev-coco.tistory.com/178
		Optional.ofNullable(requestDto.getTitle())
				.filter(title -> !title.isBlank())
				.ifPresent(post::setTitle);
		
		Optional.ofNullable(requestDto.getContent())
				.filter(content -> !content.isBlank())
				.ifPresent(post::setContent);
		
		Optional.ofNullable(requestDto.getIsSecret())
				.ifPresent(post::setSecret);
		
		boardDao.updateBoard(post);
	}
	
	/**
	 * 게시글 삭제
	 * @param username
	 * @param postId
	 * @return
	 */
	@Transactional
	public void deleteBoard(String username, long postId) {
		
		PostDto post = boardDao.selectPostById(postId);
		int userId = userDao.selectUserId(username);
		if (post==null || Long.compare(post.getUserId(), userId) != 0) {
			throw new CustomException(ErrorCode.BOARD_DELETE_FORBIDDEN);
		}
		
		// 댓글 먼저 삭제
		boardDao.deleteCommentByPostId(postId);
		
		// 게시글 삭제
		int cnt = boardDao.deleteBoardByPostId(postId);
		if (cnt==0) {
			throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
		}
	}
}
