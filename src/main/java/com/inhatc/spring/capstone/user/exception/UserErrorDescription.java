package com.inhatc.spring.capstone.user.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorDescription {
	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
	DUPLICATED_USER_ID(HttpStatus.NOT_ACCEPTABLE, "사용자의 ID가 중복되었습니다.");
	
	private final HttpStatus status;
	private final String message;
}
