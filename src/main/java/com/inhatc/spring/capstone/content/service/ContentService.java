package com.inhatc.spring.capstone.content.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.content.exception.ContentErrorDescription;
import com.inhatc.spring.capstone.content.exception.ContentException;
import com.inhatc.spring.capstone.content.repository.ContentRepository;
import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;
import com.inhatc.spring.capstone.file.entity.SavedFile;
import com.inhatc.spring.capstone.file.repository.SavedFileRepository;
import com.inhatc.spring.capstone.file.service.ContentImageService;
import com.inhatc.spring.capstone.file.service.TemporaryImageService;
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
	
	private final ContentImageService contentImageService;
	private final SavedFileRepository savedFileRepository;
	
	/** 프로젝트 게시글 생성 */
	public DisplayedContentDTO createProjectContent(NewContentDTO contentDto) throws IOException {
		Users user = userRepository.findById(contentDto.getUserId())
				.orElseThrow(
						() -> new UsersException(UserErrorDescription.NOT_FOUND_USER, 
										String.valueOf(contentDto.getUserId())
									)
					);
		
		Content content = Content.createContent(user, contentDto);
		content = contentRepository.save(content);
		
		// 저장돼 있는 이미지 정보 DB에 저장
		String modifyContentBody = contentImageService.saveContentImg(content);
		content.modifyContentBody(modifyContentBody);
		
		return DisplayedContentDTO.createdContent(content);
	}
	
	
	/** 프로젝트 게시글 조회 */
	public DisplayedContentDTO viewProjectContent(Long contentId) {
		Content content = contentRepository.findById(contentId)
				.orElseThrow(
						() -> new ContentException(ContentErrorDescription.NOT_FOUND_CONTENT, contentId)
					);
		
		// 쿠키를 받고 만약 이미 방문했다면 조회수 증가 x - 추후 구현
		content.increaseView();
		
		DisplayedContentDTO contentDetails = contentRepository.getContentDetails(contentId);
		
		return contentDetails;
	}
	
	/** 프로젝트 게시글 수정 */
	public DisplayedContentDTO modifyProjectContent(NewContentDTO contentDTO) {
		Content content = contentRepository.findById(contentDTO.getContentId())
				.orElseThrow(
						() -> new ContentException(ContentErrorDescription.NOT_FOUND_CONTENT, contentDTO.getContentId())
					);
		
		content.modifyContent(contentDTO);
		DisplayedContentDTO contentDetails = contentRepository.getContentDetails(content.getId());
		
		return contentDetails;
	}
	
	/** 프로젝트 게시글 삭제 */
	public void deleteProjectContent(Long contentId) {
		List<SavedFile> savedImgs = savedFileRepository.getContentImgs(contentId);
		for (SavedFile savedImg : savedImgs) {
			contentImageService.deleteSavedImg(savedImg);
		}
		contentRepository.deleteById(contentId);
	}
	
	
	/** 프로젝트 관련 구인 게시글 생성 */
	public DisplayedContentDTO createRecruitContent(NewContentDTO contentDto) {
		// 추후 작성
		return null;
	}
	
	
	
}
