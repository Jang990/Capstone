package com.inhatc.spring.capstone.content.service;

import java.io.IOException;
import java.util.ArrayList;
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
	
	private final ContentDocumentService contentDocumentService;
	private final TemporaryImageService tempImageService;
	
	/** 프로젝트 게시글 생성 */
	public DisplayedContentDTO createProjectContent(NewContentDTO contentDto) throws IOException {
		Users user = userRepository.findById(contentDto.getUserId())
				.orElseThrow(
						() -> new UsersException(UserErrorDescription.NOT_FOUND_USER, 
										String.valueOf(contentDto.getUserId())
									)
					);
		
		Content content = Content.createContent(user, contentDto);
		
		// content에서 이미지 Element 추출
		List<DisplayedImageDTO> tempImgDto = contentDocumentService.extractImageElement(content.getContent());
		
		// 만약 이미지가 포함된 글이라면 이미지를 서버에 저장해야 함
		if(tempImgDto.size() != 0) {
			// 1. 이미지 이동(임시저장폴더->저장폴더로 이미지 위치 변경)
			List<DisplayedImageDTO> savedImgDto = new ArrayList<>();
			String savedImgFolder = "content";
			for (DisplayedImageDTO tempImg : tempImgDto) {
				// 이동한 이미지를 다시 Temp로 되돌리는 기능 필요
				savedImgDto.add(tempImageService.moveTempFileToSavedFolder(tempImg, savedImgFolder));
			}
			
			// 2. 임시저장경로("/images/temporary/...")로 되어있는 src를 이동한 저장경로로("/images/content/...") 바꿈
			content.changeImageSrc(contentDocumentService);
			
			// 3. 파일 정보 저장
			contentImageService.saveContentImgs(content, savedImgDto);
		}
		
		content = contentRepository.save(content);
		
		
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
