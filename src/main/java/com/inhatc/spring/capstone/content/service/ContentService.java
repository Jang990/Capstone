package com.inhatc.spring.capstone.content.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.spring.capstone.content.dto.ContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.content.exception.ContentErrorDescription;
import com.inhatc.spring.capstone.content.exception.ContentException;
import com.inhatc.spring.capstone.content.repository.ContentRepository;
import com.inhatc.spring.capstone.user.entity.Users;
import com.inhatc.spring.capstone.user.exception.UserErrorDescription;
import com.inhatc.spring.capstone.user.exception.UsersException;
import com.inhatc.spring.capstone.user.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentService {
	
	private final ContentRepository contentRepository;
	private final UsersRepository userRepository;
	
	/** 프로젝트 게시글 생성 */
	public DisplayedContentDTO createProjectContent(ContentDTO contentDto) {
		Users user = userRepository.findById(contentDto.getUserId())
				.orElseThrow(
						() -> new UsersException(UserErrorDescription.NOT_FOUND_USER, 
										String.valueOf(contentDto.getUserId())
									)
					);
		
		Content content = Content.createContent(user, contentDto);
		content = contentRepository.save(content);
		
		return DisplayedContentDTO.createdContent(content);
	}
	
	
	/** 프로젝트 게시글 조회 */
	public DisplayedContentDTO viewProjectContent(Long contentId) {
		// 쿠키를 받고 만약 이미 방문했다면 조회수 증가 x - 추후 구현
		System.out.println(contentRepository.getContentView());
		
		return null;
	}
	
	/** 프로젝트 게시글 수정 */
	public ContentDTO modifyProjectContent(DisplayedContentDTO contentDTO) {
		Content content = contentRepository.findById(contentDTO.getContentId())
				.orElseThrow(
						() -> new ContentException(ContentErrorDescription.NOT_FOUND_CONTENT, contentDTO.getContentId())
					);
		
		content.modifyContent(contentDTO);
		
		return ContentDTO.of(content);
	}
	
	
	/** 프로젝트 관련 구인 게시글 생성 */
	public ContentDTO createRecruitContent(ContentDTO contentDto) {
		// 추후 작성
		return null;
	}
}
