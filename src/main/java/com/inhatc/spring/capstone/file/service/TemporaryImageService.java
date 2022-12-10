package com.inhatc.spring.capstone.file.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.inhatc.spring.capstone.content.service.ContentDocumentService;
import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;

import lombok.RequiredArgsConstructor;

/** 
 * 글을 작성하기 전 임시저장 서비스
 */
@Service
@RequiredArgsConstructor
public class TemporaryImageService {
	@Value("${temporaryLocation}")
	private String temporaryLocation;
	
	@Value(value = "${uploadPath}")
    private String uploadPath; //실제 업로드 장소
	
	private final String resourceHandlerURL = "/images/"; // 외부에서 이미지로 접근하는 경로

	
	private final FileService fileService;
	
	/** 이미지 임시저장 폴더에 저장 */
	public DisplayedImageDTO saveTemporaryImage(MultipartFile ImgFile) throws IOException {
		String oriImgName = ImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";

		if (StringUtils.isEmpty(oriImgName)) {
			throw new IllegalArgumentException();
		}
		
		BufferedImage bufferedImage = ImageIO.read(ImgFile.getInputStream());
	    int width = bufferedImage.getWidth();
	    int height = bufferedImage.getHeight();
		
		imgName = fileService.uploadFile(temporaryLocation, oriImgName, ImgFile.getBytes());
		imgUrl = "/images/temporary/" + imgName;
		
		return DisplayedImageDTO.builder()
				.width(width)
				.height(height)
				.byteSize(ImgFile.getSize())
				.originalName(oriImgName)
				.savedPath(imgUrl)
				.build();
	}
	
	/** 임시저장 파일을 실제 저장폴더로 이동 */
	public DisplayedImageDTO moveTempFileToSavedFolder(DisplayedImageDTO tempImg, String movedFolderName) throws IOException {
		String tempPath = FileService.resourcePathToSavedPath(tempImg.getSavedPath());
		String targetPath = tempPath.replaceFirst("temporary", movedFolderName);
		fileService.moveFile(tempPath, targetPath);
		
		return DisplayedImageDTO.builder()
				.width(tempImg.getWidth())
				.height(tempImg.getHeight())
				.originalName(tempImg.getOriginalName())
				.byteSize(tempImg.getByteSize())
				.savedPath(FileService.savedPathToResourcePath(targetPath))
				.build();
	}
	
}
