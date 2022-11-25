package com.inhatc.spring.capstone.content.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentErrorDescription {
	NOT_FOUND_CONTENT(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
//	DUPLICATED_USER_ID(HttpStatus.NOT_ACCEPTABLE, "사용자의 ID가 중복되었습니다.");
	
	private final HttpStatus status;
	private final String message;
}
