package com.ssafy.home.domain.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.common.page.PageRequestDto;
import com.ssafy.home.domain.board.dao.BoardDao;
import com.ssafy.home.domain.board.dto.BoardListResponseDto;
import com.ssafy.home.domain.board.dto.InsertBoardRequestDto;
import com.ssafy.home.domain.board.dto.PostDto;
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
}
