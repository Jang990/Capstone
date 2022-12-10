package com.inhatc.spring.capstone.file.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.content.service.ContentDocumentService;
import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;
import com.inhatc.spring.capstone.file.entity.SavedFile;
import com.inhatc.spring.capstone.file.repository.SavedFileRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentImageService {
	@Value("${contentImgLocation}")
	private String contentImgLocation;

	private final SavedFileRepository savedFileRepository;

	private final FileService fileService;
	private final ContentDocumentService contentDocumentService;
	private final TemporaryImageService tempImageService;

	// 이건 사용 안함
	public void updateContentImg(Long imgId, MultipartFile itemImgFile) throws IOException {
		/* if (itemImgFile.isEmpty())
			return;

		SavedFile savedImg = savedFileRepository.findById(imgId)
				.orElseThrow(EntityNotFoundException::new);

		if (!StringUtils.isEmpty(savedImg.getSavedPath())) {
			fileService.deleteFile(contentImgLocation + "/" + savedImg.getSavedPath());
		}

		String oriName = savedImg.getOriginalName();
		String imgName = fileService.uploadFile(contentImgLocation, oriName, itemImgFile.getBytes());
		String imgUrl = "/images/item/" + imgName;

		// 나중에 width, height 값 넣어주기
		savedImg.updateFile(oriName, imgName, imgUrl, 0, 0); */

	}
	
	/** 이미지 저장 */
	public void saveContentImgs(Content content, List<DisplayedImageDTO> imgsDto) {
		List<SavedFile> savedImgs = new ArrayList<>();
		for (DisplayedImageDTO img : imgsDto) {
			savedImgs.add(SavedFile.createSavedImg(content, img));
		}
		savedFileRepository.saveAll(savedImgs);
	}
	
	/** 이미지 업데이트 - width나 height 변경 예상 */
	public void updateContentImg(DisplayedImageDTO imgDto) {
		SavedFile updatedImg = savedFileRepository.findBySavedPath(imgDto.getSavedPath())
				.orElseThrow(EntityNotFoundException::new);

		updatedImg = updatedImg.modifySavedImgSize(imgDto);
		savedFileRepository.save(updatedImg);
	}
	/*
	public void modifyContentImg(Content sourceContent, NewContentDTO modifiedContent) {
		// DocService로 ImgDTO 뽑아내기
		List<DisplayedImageDTO> sourceImgs = contentDocumentService.extractImageElement(sourceContent.getContent());
		List<DisplayedImageDTO> modifiedImgs = contentDocumentService.extractImageElement(modifiedContent.getContent());
		
		// 불러온 Img 리스트와 뽑아낸 리스트 비교
		List<DisplayedImageDTO> newImgs = new ArrayList<>();
		
		List<DisplayedImageDTO> deletedImgs = new ArrayList<>();
		
		
		// 1. 사라진 것은 DB및 파일에서 지우기		2. 새로 생긴것은 새로 DB및 파일에 저장
	}
	*/
	
	/** 이미지 삭제 */
	public void deleteSavedImg(DisplayedImageDTO img) {
		SavedFile image = savedFileRepository.findBySavedPath(img.getSavedPath())
				.orElseThrow(EntityNotFoundException::new);
		fileService.deleteFile(img.getSavedPath());
		savedFileRepository.delete(image);
	}
	
	/** 해당 컨텐츠에 이미지 삭제 */
	public void deleteSavedContentImg(Long contentId) {
		List<SavedFile> savedImgs = savedFileRepository.getContentImgs(contentId);
		for (SavedFile savedImg : savedImgs) {
			String savedImgPath = FileService.resourcePathToSavedPath(savedImg.getSavedPath());
			fileService.deleteFile(savedImgPath);
			savedFileRepository.delete(savedImg);
		}
	}
}
