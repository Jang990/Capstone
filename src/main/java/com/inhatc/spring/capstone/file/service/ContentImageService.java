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

	/** 
	 * 1. 임시저장폴더 -> 저장폴더 이미지 이동 <br>
	 * 2. 본문 <img src="...">부분 변경 <br>
	 * 3. 파일 정보 저장
	 * */
	public String saveContentImg(Content content) throws IOException {
		String savedImgFolder = "content";
		String contentSource = content.getContent();
		// content에서 이미지 Element 추출
		List<DisplayedImageDTO> tempImgDto = contentDocumentService.extractImageElement(contentSource);
		
		// 1. 이미지 이동(임시저장폴더->저장폴더로 이미지 위치 변경)
		List<DisplayedImageDTO> savedImgDto = tempImageService.convertTempImgToSavedImg(tempImgDto, savedImgFolder);		
		
		// 2. 임시저장경로로 되어있는 src를 이동한 저장경로로 바꿈
		String changedContentBody = contentDocumentService.changeImageSorce(contentSource, savedImgFolder);
		
		// 3. 파일 정보 저장
		List<SavedFile> savedImgs = new ArrayList<>();
		for (DisplayedImageDTO img : savedImgDto) {
			savedImgs.add(SavedFile.createSavedImg(content, img));
		}
		savedFileRepository.saveAll(savedImgs);
		
		// 저장경로 변경으로 인한 ContentBody부분을 수정해서 리턴
		return changedContentBody;
	}
}
