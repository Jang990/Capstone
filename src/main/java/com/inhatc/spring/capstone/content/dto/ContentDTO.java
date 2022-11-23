package com.inhatc.spring.capstone.content.dto;

import java.util.ArrayList;
import java.util.List;

import com.inhatc.spring.capstone.entity.file.SavedFile;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentDTO {
	private Long userId;
	private String userEmail; // userId만 있어도 될 것 같다? 

	private String title; // 제목
	private String content; // 내용
	private String usedLanguage; // 사용 언어 - 나중에 GitHub API를 사용하면서 타입을 바꿀 것
	private int viewCount; // 조회수 - 쿠키로 조회수 중복을 제거할 것이다.
	private int voteCount; // 찬반 카운트
	
	private boolean isRecruit;
	
	List<SavedFile> files = new ArrayList<>();
}
