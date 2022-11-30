package com.inhatc.spring.capstone.file.service;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.inhatc.spring.capstone.file.entity.SavedFile;
import com.inhatc.spring.capstone.file.repository.SavedFileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentImageService {
	@Value("${itemImgLocation}")
	private String itemImgLocation;

	private final SavedFileRepository savedFileRepository;

	private final FileService fileService;

	public void saveContentImg(SavedFile savedImage, MultipartFile ImgFile) throws IOException {
		String oriImgName = ImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";

		if (!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, ImgFile.getBytes());
			imgUrl = "/images/item/" + imgName;
		}

		// 나중에 width, height 값 넣어주기
		savedImage.updateFile(oriImgName, imgName, imgUrl, 0, 0);
		savedFileRepository.save(savedImage);
	}

	public void updateContentImg(Long imgId, MultipartFile itemImgFile) throws IOException {
		if (itemImgFile.isEmpty())
			return;

		SavedFile savedImg = savedFileRepository.findById(imgId)
				.orElseThrow(EntityNotFoundException::new);

		if (!StringUtils.isEmpty(savedImg.getName())) {
			fileService.deleteFile(itemImgLocation + "/" + savedImg.getName());
		}

		String oriName = savedImg.getOriginalName();
		String imgName = fileService.uploadFile(itemImgLocation, oriName, itemImgFile.getBytes());
		String imgUrl = "/images/item/" + imgName;

		// 나중에 width, height 값 넣어주기
		savedImg.updateFile(oriName, imgName, imgUrl, 0, 0);

	}
}
