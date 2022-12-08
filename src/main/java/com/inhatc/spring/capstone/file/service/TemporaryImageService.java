package com.inhatc.spring.capstone.file.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.inhatc.spring.capstone.file.dto.TempImageDTO;

import lombok.RequiredArgsConstructor;

/** 
 * 글을 작성하기 전 임시저장 서비스
 */
@Service
@RequiredArgsConstructor
public class TemporaryImageService {
	@Value("${temporaryLocation}")
	private String temporaryLocation;
	
	private final FileService fileService;
	
	public TempImageDTO saveTemporaryImage(MultipartFile ImgFile) throws IOException {
		String oriImgName = ImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";

		if (StringUtils.isEmpty(oriImgName)) {
			throw new IllegalArgumentException();
		}
		
		imgName = fileService.uploadFile(temporaryLocation, oriImgName, ImgFile.getBytes());
		imgUrl = "/images/temporary/" + imgName;
		
		return TempImageDTO.builder()
				.byteSize(ImgFile.getSize())
				.originalName(oriImgName)
				.savedPath(imgUrl)
				.build();
	}
	
}
