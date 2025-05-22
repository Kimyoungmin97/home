package com.ssafy.home.exception;

// @SuppressWarnings("serial") // 직렬화 관련 컴파일 경고를 사용하지 않도록 임시 설정
public class RecordNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L; // 직렬화/역직렬화 시 클래스 버전 일치 여부 확인용 (뒤에 오는 숫자 아무거나 상관 없)
	
	public RecordNotFoundException(String msg) {
		super(msg);
	}
}
