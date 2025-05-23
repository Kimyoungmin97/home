package com.ssafy.home.domain.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.home.domain.board.dto.BoardDetailResponseDto;
import com.ssafy.home.domain.board.dto.BoardListResponseDto;
import com.ssafy.home.domain.board.dto.CommentDto;
import com.ssafy.home.domain.board.dto.CommentResponseDto;
import com.ssafy.home.domain.board.dto.PostDto;

@Mapper
public interface BoardDao {
	
	/**
	 * 게시글 등록
	 * @param postDto
	 * @return
	 */
	int insertBoard(PostDto postDto);
	
	/**
	 * 페이징 된 목록 조회
	 * @param offset
	 * @param size
	 * @return
	 */
	List<BoardListResponseDto> selectBoardList(@Param("offset") int offset, @Param("size") int size);
	
	/**
	 * 전체 게시글 개수
	 */
	long selectBoardCount();
	
	BoardDetailResponseDto selectBoardDetail(long postId);
	List<CommentResponseDto> selectCommentsByPostId(long postId);
	
	/**
	 * 댓글 등록 (작성)
	 */
	int insertComment(CommentDto comment);
	
	/**
	 * 수정
	 */
	PostDto selectPostById(long postId);
	int updateBoard(PostDto post);
}
