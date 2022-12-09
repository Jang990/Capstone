package com.inhatc.spring.capstone.file.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;

@SpringBootTest
class TemporaryImageServiceTest {

	@Autowired
	TemporaryImageService tempImgService;
	
	final String fileName = "rogo"; //파일명
	final String contentType = "png"; //파일타입
	final String filePath = "src/main/resources/static/img/"+fileName+"."+contentType; //파일경로
	
	@Test
	@DisplayName("파일 임시 저장")
	void testSaveTemporaryImage() throws IOException {
		//Given
    	FileInputStream fileInputStream = new FileInputStream(filePath);
		
    	//when
    	MockMultipartFile tempImage = new MockMultipartFile(fileName, fileName+"."+contentType, contentType, fileInputStream);
		DisplayedImageDTO savedTempImage = tempImgService.saveTemporaryImage(tempImage);
		
		//then
		assertEquals(tempImage.getOriginalFilename(), savedTempImage.getOriginalName());
		assertEquals(ImageIO.read(tempImage.getInputStream()).getWidth(), savedTempImage.getWidth());
		assertEquals(ImageIO.read(tempImage.getInputStream()).getHeight(), savedTempImage.getHeight());
		
		String[] savedLoc = savedTempImage.getSavedPath().substring(1).split("/");
		assertEquals("images", savedLoc[0]);
		assertEquals("temporary", savedLoc[1]);
		
	}

	@Test
	@DisplayName("임시 저장한 파일을 실제 폴더로 이동")
	void testConvertTempImgToSavedImg() throws IOException {
		//Given
		final String savedFolderName = "content";
    	FileInputStream fileInputStream = new FileInputStream(filePath);
    	MockMultipartFile tempImage = new MockMultipartFile(fileName, fileName+"."+contentType, contentType, fileInputStream);
    	DisplayedImageDTO savedTempImage = tempImgService.saveTemporaryImage(tempImage);
		
    	List<DisplayedImageDTO> tempImgList = new ArrayList<>();
    	System.out.println("========>" + savedTempImage);
    	tempImgList.add(savedTempImage);
    	
    	//when
    	DisplayedImageDTO savedImage = tempImgService.convertTempImgToSavedImg(tempImgList, savedFolderName).get(0);
		
    	
    	// then
    	String[] savedTempLoc = savedTempImage.getSavedPath().substring(1).split("/");
    	String[] savedLoc = savedImage.getSavedPath().substring(1).split("/");
    	
		assertEquals("images", savedTempLoc[0]);
		assertEquals(savedFolderName, savedLoc[1]);
		assertEquals(savedTempLoc[2], savedLoc[2]);
	}

}
