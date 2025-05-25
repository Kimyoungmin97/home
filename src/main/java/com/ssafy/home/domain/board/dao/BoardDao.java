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
	
	/** 게시글 관련 **/
	int insertBoard(PostDto postDto);
	
	BoardDetailResponseDto selectBoardDetail(long postId);
	
	PostDto selectPostById(long postId);
	
	List<BoardListResponseDto> selectBoardsByPage(@Param("offset") int offset, @Param("size") int size);
	
	long selectBoardCount();
	
	List<BoardListResponseDto> findBoardsByUserId(int userId);
	
	int updateBoard(PostDto post);
	
	int deleteBoardByPostId(long postId);
	
	/** 댓글 관련 **/
	int insertComment(CommentDto comment);
	
	List<CommentResponseDto> selectCommentsByPostId(long postId);
	
	CommentResponseDto selectCommentById(long postId, long commentId);

	int updateComment(long postId, long commentId, String content, int userId);

	int deleteAllCommentsByPostId(long postId);

	int deleteComment(long postId, long commentId, int userId);
	
}
