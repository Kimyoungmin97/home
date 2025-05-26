package com.ssafy.home.domain.user.controller;

import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.exception.ErrorCode;
import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.domain.house.service.HouseService;
import com.ssafy.home.domain.user.dto.RegistUserRequestDto;
import com.ssafy.home.domain.user.dto.User;
import com.ssafy.home.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping
    private ResponseEntity<ApiResponse> registMember(@RequestBody User user) {
        try {
        	userService.insert(user);
            return ResponseEntity.ok(ApiResponse.success(Map.of("user", user)));
        } catch (DataAccessException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
