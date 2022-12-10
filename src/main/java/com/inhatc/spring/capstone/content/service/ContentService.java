package com.inhatc.spring.capstone.content.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private final String contentSavedFolderName = "content"; 
	
	private final ContentRepository contentRepository;
	private final UsersRepository userRepository;
	private final SavedFileRepository savedFileRepository;
	
	private final ContentImageService contentImageService;
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
		if(tempImgDto.size() > 0) {
			saveTempContentImg(content, tempImgDto);
		}
		
		content = contentRepository.save(content);
		
		
		return DisplayedContentDTO.createdContent(content);
	}
	
	
	
	
	private void saveTempContentImg(Content content, List<DisplayedImageDTO> tempImgDto) throws IOException {
		// 1. 이미지 이동(임시저장폴더->저장폴더로 이미지 위치 변경)
		List<DisplayedImageDTO> savedImgDto = new ArrayList<>();
		for (DisplayedImageDTO tempImg : tempImgDto) {
			// 이동한 이미지를 다시 Temp로 되돌리는 기능 필요
			savedImgDto.add(tempImageService.moveTempFileToSavedFolder(tempImg, contentSavedFolderName));
		}
		
		// 2. 임시저장경로("/images/temporary/...")로 되어있는 src를 이동한 저장경로로("/images/content/...") 바꿈
		content.changeImageSrc(contentDocumentService);
		
		// 3. 파일 정보 저장
		contentImageService.saveContentImgs(content, savedImgDto);
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
	public DisplayedContentDTO modifyProjectContent(NewContentDTO modifiedContentDTO) throws IOException {
		Content sourceContent = contentRepository.findById(modifiedContentDTO.getContentId())
				.orElseThrow(
						() -> new ContentException(ContentErrorDescription.NOT_FOUND_CONTENT, modifiedContentDTO.getContentId())
					);
		
		List<DisplayedImageDTO> sourceImgs = contentDocumentService.extractImageElement(sourceContent.getContent()); 
		List<DisplayedImageDTO> modifiedImgs = contentDocumentService.extractImageElement(modifiedContentDTO.getContent());
		
		sourceContent.modifyContent(modifiedContentDTO);
		
		// 이미지가 없는 콘텐츠임
		if(modifiedImgs.size() == 0 && sourceImgs.size() == 0) {
			// 아무일 없음
		}
		// 수정하면서 아예 없던 이미지가 새로 생김 - 전부 임시저장->저장
		else if(modifiedImgs.size() > 0 && sourceImgs.size() == 0) {
			saveTempContentImg(sourceContent, modifiedImgs);
		}
		// 수정하면서 이미지가 전부 삭제됨 - 전부 삭제
		else if(sourceImgs.size() > 0 && modifiedImgs.size() == 0) {
			contentImageService.deleteSavedContentImg(sourceContent.getId());
		}
		// 이미지가 있음 - 변경을 체크해야 함
		else {
			// modifiedImgs에 temporary에 저장된것은 새로운 것이다.
			List<DisplayedImageDTO> deleteImgs = new ArrayList<>(); // 삭제된 이미지
			List<DisplayedImageDTO> existImgs = new ArrayList<>(); // 기존 존재하던 이미지
			
			// source에 있고 modif에 없는 것은 삭제
			sourceImgs.stream().forEach((imgDto) -> {
				if(!modifiedImgs.contains(imgDto))
					deleteImgs.add(imgDto);
				else
					existImgs.add(imgDto);
			});
			
			// 사라진 경우 delete
			for (DisplayedImageDTO img : deleteImgs) {
				contentImageService.deleteSavedImg(img);
			}
			
			List<DisplayedImageDTO> checkImgs = new ArrayList<>(); // 변화가 있거나 새로 생긴 이미지
			modifiedImgs.stream().forEach((imgDto) -> {
				if(!existImgs.contains(imgDto)) 
					checkImgs.add(imgDto);
			});
			
			List<DisplayedImageDTO> tempImgs = new ArrayList<>(); // 임시 저장소에 있는 이미지
			List<DisplayedImageDTO> updateImgs = new ArrayList<>(); // 변화가 생긴 이미지
			// 변화가 있는 이미지
			for (DisplayedImageDTO img : checkImgs) {
				// 임시저장소에 저장된 새로 생긴 이미지
				if(img.getSavedPath().substring(1).split("/")[1].equals("temporary")) {
					tempImgs.add(img);
				}
				// 기존 파일이지만 변화가 있는 이미지 (ex) width, hegith)
				else {
					updateImgs.add(img);
				}
			}
			
			saveTempContentImg(sourceContent, tempImgs);
			
			for (DisplayedImageDTO updateImg : updateImgs) {
				contentImageService.updateContentImg(updateImg);
			}
		}
		
		
		DisplayedContentDTO contentDetails = contentRepository.getContentDetails(sourceContent.getId());
		
		return contentDetails;
	}
	
	/** 프로젝트 게시글 삭제 */
	public void deleteProjectContent(Long contentId) {
		contentImageService.deleteSavedContentImg(contentId);
		contentRepository.deleteById(contentId);
	}
	
	
	/** 프로젝트 관련 구인 게시글 생성 */
	public DisplayedContentDTO createRecruitContent(NewContentDTO contentDto) {
		// 추후 작성
		return null;
	}
	
	
	
}
