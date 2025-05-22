package com.ssafy.home.common.util;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 객체를 JSON 으로 변환
 */
public interface ControllerHelper {
	
	// 결과 전송 helper 메소드
    default void handleJsonResponse(HttpServletResponse response, Object data, HttpStatus status) {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status.value());
        try {
            String json = new ObjectMapper().writeValueAsString(data);
            response.getWriter().write(json);
        } catch (IOException e) {
//            log.error("Error writing JSON response", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
