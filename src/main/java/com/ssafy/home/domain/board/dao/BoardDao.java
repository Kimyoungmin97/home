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
	
	int insertBoard(PostDto postDto);
	
	List<BoardListResponseDto> selectBoardsByPage(@Param("offset") int offset, @Param("size") int size);
	
	long selectBoardCount();
	
	BoardDetailResponseDto selectBoardDetail(long postId);

	List<CommentResponseDto> selectCommentsByPostId(long postId);

	int insertComment(CommentDto comment);
	
	PostDto selectPostById(long postId);
	int updateBoard(PostDto post);

	int deleteAllCommentsByPostId(long postId);
	
	int deleteBoardByPostId(long postId);
}
