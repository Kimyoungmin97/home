package com.ssafy.home.common.security.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ssafy.home.domain.user.dto.User;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UserDetails 구현체 (Spring security 인증 주체 정의)
 */
@Data
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
	
	private @NonNull User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> roles = new ArrayList<>();
		if (user.getRole() != null) {
			String roleName = "ROLE_" + user.getRole();
			log.debug("권한 등록됨: {}", roleName);
			roles.add(new SimpleGrantedAuthority(roleName));
		}
		return roles;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public String getUsername() {
		return user.getUsername();
	}

}
