package com.inhatc.spring.capstone.content.dto;

import java.util.ArrayList; 
import java.util.List;

import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
/** 콘텐츠 수정 또는 생성 시 사용되는 DTO */
public class NewContentDTO {
	private Long contentId; // 수정시에 필요함
	
	private Long userId;
	private String userEmail; // userId만 있어도 될 것 같다? 

	private String title; // 제목
	private String content; // 내용
	
	private String usedLanguage; // 사용 언어 - 나중에 GitHub API를 사용하면서 타입을 바꿀 것
	private int People;//언어별 인원
	private String Selectbox;//백엔드 프론트엔드
	
	private boolean isRecruit;
	
	List<DisplayedImageDTO> images = new ArrayList<>();
	List<DisplayedTagDTO> tags = new ArrayList<>();

	@Builder
	public NewContentDTO(Long contentId, Long userId, String userEmail, String title, String content,int people,
			String Selectbox,String usedLanguage, boolean isRecruit, List<DisplayedImageDTO> images, List<DisplayedTagDTO> tags) {
		this.contentId = contentId;
		this.userId = userId;
		this.userEmail = userEmail;
		this.title = title;
		
		this.People = people;
		this.Selectbox = Selectbox;
		this.content = content;
		this.usedLanguage = usedLanguage;
		this.isRecruit = isRecruit;
		this.images = images;
		this.tags = tags;
	}
	
}
