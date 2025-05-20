package com.ssafy.home.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController // @RequestBody 생략 가능
@RequestMapping("/house")
@RequiredArgsConstructor
public class HouseController {

	@GetMapping
	public ResponseEntity<ApiResponse<Void>> list(){
		return ResponseEntity.ok(ApiResponse.success());
	}
	
	
}
