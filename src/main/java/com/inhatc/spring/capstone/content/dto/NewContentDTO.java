package com.inhatc.spring.capstone.content.dto;

import java.util.ArrayList; 

import java.util.List;

import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
/** 콘텐츠 수정 또는 생성 시 사용되는 DTO */
public class NewContentDTO {
	private Long contentId; // 수정시에 필요함
	
	private String userEmail;

	private String title; // 제목
	private String content; // 내용
	
	private String usedLanguage; // 사용 언어 - 나중에 GitHub API를 사용하면서 타입을 바꿀 것
	private String iconPath; // 사용언어 아이콘 path - 추가됐다.
	
	private boolean isRecruit;
	
	List<DisplayedImageDTO> images = new ArrayList<>();
	List<DisplayedTagDTO> tags = new ArrayList<>();

	@Builder
	public NewContentDTO(Long contentId, String userEmail, String title, String content,
			String usedLanguage, boolean isRecruit, List<DisplayedImageDTO> images, List<DisplayedTagDTO> tags) {
		this.contentId = contentId;
		this.userEmail = userEmail;
		this.title = title;
		
	
		this.content = content;
		this.usedLanguage = usedLanguage;
		this.isRecruit = isRecruit;
		this.images = images;
		this.tags = tags;
	}
	
}
