package com.ssafy.home.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

	private boolean success;
	private int code;
	private String message;
	private T data;
	
	// 성공 응답 (데이터 포함)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, 200, "요청이 성공했습니다.", data);
    }

    // 성공 응답 (데이터 없음)
    public static ApiResponse<Void> success() {
        return new ApiResponse<>(true, 200, "요청이 성공했습니다.", null);
    }

    // 실패 응답
    public static ApiResponse<Void> fail(int code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }
    
    // 실패 응답
    public static <T> ApiResponse<T> fail(int code, String message, T data) {
        return new ApiResponse<>(false, code, message, null);
    }
}
