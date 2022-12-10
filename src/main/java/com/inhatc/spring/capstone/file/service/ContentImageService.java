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
	
	/** 이미지 삭제 */
	public void deleteSavedImg(SavedFile savedImg) {
		String savedImgPath = FileService.resourcePathToSavedPath(savedImg.getSavedPath());
		fileService.deleteFile(savedImgPath);
		savedFileRepository.delete(savedImg);
	}
}
