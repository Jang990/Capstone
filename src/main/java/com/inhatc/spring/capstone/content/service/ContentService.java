package com.inhatc.spring.capstone.content.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.content.exception.ContentErrorDescription;
import com.inhatc.spring.capstone.content.exception.ContentException;
import com.inhatc.spring.capstone.content.repository.ContentRepository;
import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;
import com.inhatc.spring.capstone.file.repository.SavedFileRepository;
import com.inhatc.spring.capstone.file.service.ContentImageService;
import com.inhatc.spring.capstone.file.service.TemporaryImageService;
import com.inhatc.spring.capstone.tag.constant.TagType;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.tag.entity.Tag;
import com.inhatc.spring.capstone.tag.repository.TagRepository;
import com.inhatc.spring.capstone.tag.service.ContentTagService;
import com.inhatc.spring.capstone.tag.service.TagService;
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
	private final TagRepository tagRepository;
	
	private final ContentImageService contentImageService;
	private final ContentDocumentService contentDocumentService;
	private final TemporaryImageService tempImageService;
	private final TagService tagService;
	private final ContentTagService contentTagService;
	
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
		
		
		List<DisplayedTagDTO> tags = contentDto.getTags();
		if(tags != null && tags.size() > 0) {
			// 컨텐츠와 관련된 태그들 저장하고 가져오기
			Set<Tag> savedTags = contentTagService.saveContentTags(tags);
			content.setSavedTags(savedTags);
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
		System.out.println("소스 속 이미지들===");
		for (DisplayedImageDTO displayedImageDTO : sourceImgs) {
			System.out.println(displayedImageDTO);
		}
		System.out.println();
		List<DisplayedImageDTO> modifiedImgs = contentDocumentService.extractImageElement(modifiedContentDTO.getContent());
		System.out.println("바뀐 소스 속에 이미지들===");
		for (DisplayedImageDTO displayedImageDTO : modifiedImgs) {
			System.out.println(displayedImageDTO);
		}
		
		sourceContent.modifyContent(modifiedContentDTO);
		
		// 이미지가 없는 콘텐츠임
		if(modifiedImgs.size() == 0 && sourceImgs.size() == 0) {
			// 아무일 없음
			System.out.println("이미지 없어요");
		}
		// 수정하면서 아예 없던 이미지가 새로 생김 - 전부 임시저장->저장
		else if(modifiedImgs.size() > 0 && sourceImgs.size() == 0) {
			System.out.println("이미지 전부 저장");
			saveTempContentImg(sourceContent, modifiedImgs);
		}
		// 수정하면서 이미지가 전부 삭제됨 - 전부 삭제
		else if(sourceImgs.size() > 0 && modifiedImgs.size() == 0) {
			System.out.println("이미지 전부 삭제");
			contentImageService.deleteSavedContentImg(sourceContent.getId());
		}
		// 이미지가 있음 - 혹시모를 변경을 체크해야 함
		else {
			// 이 부분만 오류가난다. 나머지는 다 정상 작동
			
			System.out.println("이미지 변동");
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
			System.out.println("===삭제한 이미지");
			for (DisplayedImageDTO img : deleteImgs) {
				System.out.println("삭제: " + img);
				contentImageService.deleteSavedImg(img);
			}
			
			List<DisplayedImageDTO> checkImgs = new ArrayList<>(); // 변화가 있거나 새로 생긴 이미지
			modifiedImgs.stream().forEach((imgDto) -> {
				System.out.println("===체크할 이미지");
				if(!existImgs.contains(imgDto)) {
					System.out.println("체크: " + imgDto);
					checkImgs.add(imgDto);
				}
			});
			
			List<DisplayedImageDTO> tempImgs = new ArrayList<>(); // 임시 저장소에 있는 이미지
			List<DisplayedImageDTO> updateImgs = new ArrayList<>(); // 변화가 생긴 이미지
			// 변화가 있는 이미지
			System.out.println("===변화한이미지");
			for (DisplayedImageDTO img : checkImgs) {
				// 임시저장소에 저장된 새로 생긴 이미지
				if(img.getSavedPath().substring(1).split("/")[1].equals("temporary")) {
					System.out.println("임시저장되어 있는 이미지: " + img);
					tempImgs.add(img);
				}
				// 기존 파일이지만 변화가 있는 이미지 (ex) width, hegith)
				else {
					System.out.println("사이즈 변화 이미지:" + img);
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
