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
	
	/**
	 * username으로 userId 조회
	 * 존재하지 않으면 USER_NOT_FOUND 예외 발생
	 * 
	 * @param username 사용자 아이디 (이메일 등)
	 * @return DB에 저장된 userId
	 */
	private int getUserId(String username) {
		Integer userId = userDao.selectUserIdByUsername(username);
		if (userId == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
		return userId;
	}
	
	/**
	 * 게시글 목록 조회 (페이징)
	 * @param pageRequestDto 페이지 요청정보
	 * @return 게시글 리스트
	 */
	public List<BoardListResponseDto> getBoardList(PageRequestDto pageRequestDto) {
		return boardDao.selectBoardsByPage(pageRequestDto.getOffset(), pageRequestDto.getSize());
	}
	
	/**
	 * 전체 게시글 수 조회
	 * @return 총 게시글 수
	 */
	public long getTotalCount() {
		return boardDao.selectBoardCount();
	}
	
	/**
	 * 게시글 상세 조회
	 * @param postId 게시글ID
	 * @return 게시글 상세 정보
	 */
	public BoardDetailResponseDto getBoardDetail(long postId) {
		BoardDetailResponseDto post = boardDao.selectBoardDetail(postId);
		if (post==null) throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
		
		return post;
	}
	
	/**
	 * 게시글 등록
	 * @param requestDto
	 * @param username
	 * @return
	 */
	public int insertBoard(InsertBoardRequestDto requestDto, String username) {
		// 1. users 테이블에서 user_id 조회해옴
		int userId = getUserId(username);
		
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

	
	/**
	 * 게시글 수정
	 * 
	 * @param postId
	 * @param requestDto
	 * @param username
	 */
	public void updateBoard(long postId, UpdateBoardRequestDto requestDto, String username) {
		
		PostDto post = boardDao.selectPostById(postId);
		if (post == null) {
			throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
		}
		
		int userId = getUserId(username);
		if (Integer.compare(post.getUserId(), userId) != 0) {
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
	 * 
	 * @param username
	 * @param postId
	 * @return
	 */
	@Transactional
	public void deleteBoard(String username, long postId) {
		
		PostDto post = boardDao.selectPostById(postId);
		int userId = getUserId(username);
		if (post==null || Integer.compare(post.getUserId(), userId) != 0) {
			throw new CustomException(ErrorCode.BOARD_DELETE_FORBIDDEN);
		}
		
		// 댓글 먼저 삭제
		boardDao.deleteAllCommentsByPostId(postId);
		
		// 게시글 삭제
		int cnt = boardDao.deleteBoardByPostId(postId);
		if (cnt==0) {
			throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
		}
	}
	
	/**
	 * 특정 게시판에 댓글 또는 대댓글 등록
	 * 
	 * @param requestDto
	 * @param username
	 * @param postId
	 * @return
	 */
	public int insertComment(InsertCommentRequestDto requestDto, String username, long postId) {

		int userId = getUserId(username);
				
		CommentDto.CommentDtoBuilder builder = CommentDto.builder()
				.postId(postId)
				.userId(userId)	
				.content(requestDto.getContent());
		
		if (requestDto.getParentId()!=null) {
			builder.parentId(requestDto.getParentId());
		}
		
		CommentDto comment = builder.build();
		
		return boardDao.insertComment(comment);
	}
	
}
