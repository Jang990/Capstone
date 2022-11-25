package com.inhatc.spring.capstone.content.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.inhatc.spring.capstone.user.dto.DisplayedUserDTO;

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
	private LocalDateTime createdDate; // 작성일
	private String usedLanguage; // 사용 언어
	private boolean isRecruit;
	private int viewCount; // 조회 수
	
	List<DisplayedCommentDTO> comments;
	List<DisplayedFileDTO> files = new ArrayList<>();
	
	private int voteCount; // 찬반 카운트
	
	
}
