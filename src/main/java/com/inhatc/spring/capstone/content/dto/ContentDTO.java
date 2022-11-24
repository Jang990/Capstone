package com.inhatc.spring.capstone.content.dto;

import java.util.ArrayList;
import java.util.List;

import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.entity.file.SavedFile;
import com.inhatc.spring.capstone.user.entity.Users;

import lombok.Builder;
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

	@Builder
	public ContentDTO(Long userId, String userEmail, String title, String content, String usedLanguage, int viewCount,
			int voteCount, boolean isRecruit, List<SavedFile> files) {
		this.userId = userId;
		this.userEmail = userEmail;
		this.title = title;
		this.content = content;
		this.usedLanguage = usedLanguage;
		this.viewCount = viewCount;
		this.voteCount = voteCount;
		this.isRecruit = isRecruit;
		this.files = files;
	}
	
	public static ContentDTO of(Content content) {
		return ContentDTO.builder()
				.userId(content.getWriter().getId())
				.userEmail(content.getWriter().getEmail())
				.title(content.getTitle())
				.content(content.getContent())
				.usedLanguage(content.getUsedLanguage())
				.viewCount(content.getViewCount())
				.voteCount(content.getVoteCount())
				.isRecruit(content.isRecruit())
				.build();
	}
}
