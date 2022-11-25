package com.inhatc.spring.capstone.content.repository;

import static com.inhatc.spring.capstone.content.entity.QContent.content1;

import java.util.List;

import com.inhatc.spring.capstone.content.entity.Content;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContentRepositoryImpl implements ContentRepositoryCustom {
	private final JPAQueryFactory query;
	
	public String getContentView() {
		List<Content> contentList = query.select(content1).from(content1).fetch();
		for (Content content : contentList) {
			System.out.println("제목: " + content.getTitle());
			System.out.println("사용언어: " + content.getUsedLanguage());
		}
		
		return "연결 테스트 문자열";
		
	}
}
