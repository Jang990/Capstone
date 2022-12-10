package com.inhatc.spring.capstone.content.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.user.dto.DisplayedUserDTO;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
/** 사용자가 게시물에 들어왔을 때 보여질 정보 */
public class DisplayedContentDTO {
	private Long contentId; // 게시글 ID
	private DisplayedUserDTO writer; // 작성자 DTO
	private String title; // 제목
	private String content; // 내용
	private LocalDateTime accessDate; // 최근 작성 또는 수정일
	private String usedLanguage; // 사용 언어
	private boolean isRecruit;
	private int viewCount; // 조회 수
	
	
	List<DisplayedCommentDTO> comments;
	List<DisplayedFileDTO> files = new ArrayList<>();
	
	private int voteCount; // 찬반 카운트
	
	public static DisplayedContentDTO createdContent(Content content) {
		return DisplayedContentDTO.builder()
				.contentId(content.getId())
				.writer(new DisplayedUserDTO(content.getWriter().getName(), content.getWriter().getEmail()))
				.title(content.getTitle())
				.content(content.getContent())
				.accessDate(content.getDate_created())
				.usedLanguage(content.getUsedLanguage())
				.isRecruit(content.isRecruit())
				.viewCount(0)
				.comments(new ArrayList<>())
				.files(new ArrayList<>()) // 나중에 파일 입출력 모듈 구현 후 수정
				.build();
	}
	
	@Builder
	@QueryProjection
	public DisplayedContentDTO(Long contentId, DisplayedUserDTO writer, String title, 
			String content, LocalDateTime accessDate, String usedLanguage, 
			boolean isRecruit, int viewCount, List<DisplayedCommentDTO> comments, 
			List<DisplayedFileDTO> files, int voteCount) {
		this.contentId = contentId;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.accessDate = accessDate;
		this.usedLanguage = usedLanguage;
		this.isRecruit = isRecruit;
		this.viewCount = viewCount;
		this.comments = comments;
		this.files = files;
		this.voteCount = voteCount;
	}
	
	
}
