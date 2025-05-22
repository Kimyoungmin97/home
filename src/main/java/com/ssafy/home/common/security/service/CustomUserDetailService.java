package com.ssafy.home.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.home.common.security.dto.CustomUserDetails;
import com.ssafy.home.domain.user.dao.UserDao;
import com.ssafy.home.domain.user.dto.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User user = userDao.select(username);
    	if (user == null) throw new UsernameNotFoundException(username);
    	return new CustomUserDetails(user);
    }
}
