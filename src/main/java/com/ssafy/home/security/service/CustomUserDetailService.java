package com.ssafy.home.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.home.model.user.dao.UserDao;
import com.ssafy.home.model.user.dto.User;
import com.ssafy.home.security.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
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
