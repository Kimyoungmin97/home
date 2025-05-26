package com.ssafy.home.common.security.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ssafy.home.domain.user.dto.User;

import lombok.extern.slf4j.Slf4j;

// 회원 비밀번호 암호화 자동 처리 (회원 가입 시, DB에 insert 되기 전에 비밀번호를 PasswordEncoder 로 암호화
// @Before 로 DAO 계층 호출 전에 실행됨

@Aspect
@Component
@Slf4j
public class UserAspect {
    @Autowired
    PasswordEncoder encoder;
    
    // DAO 클래스의 insert() 메서드 중 파라미터로 User 을 받는 메서드에만 동작
    @Before("execution( * com.ssafy..dao.*.insert(com.ssafy.home.domain.user.dto.User)) && args(user)")
    public void encodeMemberPassword(User user) {
    	user.setPassword(encoder.encode(user.getPassword()));
        // insert 호출 전에 Member 객체의 password 필드를 암호화된 값으로 덮어씀
    }
}
