package com.ssafy.home.domain.user.service;

import org.springframework.stereotype.Service;

import com.ssafy.home.domain.user.dao.UserDao;
import com.ssafy.home.domain.user.dto.User;
import com.ssafy.home.exception.RecordNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserDao userDao;
	
	// try-catch 사용 X - 서비스 로직은 깔끔하게 예외만 던지는 쪽이 유지보수가 좋다고 함
	// RecordNotFoundException 는 RuntimeException(비체크 예외) 이기 때문에 try-catch 없이 던져도 ControllerAdvice 나 글로벌 예외 처리기가 처리함
	public User login(String username, String password) {
		User user = userDao.selectAllByUsername(username);
		if (user != null && user.getPassword().equals(password)) {
			return user;
		} else {
			throw new RecordNotFoundException("id/password 확인");
		}
	}
	
	public int update(User user) {
		return userDao.update(user);
	}
	
	public User selectDetail(String username) {
        return userDao.selectAllByUsername(username);
    }
	
	public int selectUserId(String username) {
		return userDao.selectUserIdByUsername(username);
	}
}
