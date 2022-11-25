package com.inhatc.spring.capstone.content.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContentRepositoryImpl implements ContentRepositoryCustom {
	private final JPAQueryFactory query;
	
	public String getContentView() {
		return "연결 테스트 문자열";
	}
}
