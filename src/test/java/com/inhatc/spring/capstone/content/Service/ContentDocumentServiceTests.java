package com.inhatc.spring.capstone.content.Service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inhatc.spring.capstone.content.service.ContentDocumentService;

@SpringBootTest
class ContentDocumentServiceTests {

	@Autowired
	ContentDocumentService contentDocumentService;
	
	final String contentBody = "" 
			+ "<div>"
			+ "<img src='localhost:8080/images/temporary/222d6b0e-818d-47ea-a9f5-cfac9dc0a242.png' style='width=1920px; height=1080px;' alt='Lost Ark Screenshot 2021.08.19 - 20.09.30.39.png' bytesize='3661570'> 이상한 글귀<br>"
			+ "<h2>제목내용</h2>"
			+ "<img src='localhost:8080/images/temporary/222d6b0e-818d-47ea-a9f5-cfac9dc0a242.png' style='height=480px; width=720px;' alt='두번째 사진.png' bytesize='11111'> 이상한 글귀<br>"
			+ "<div>필요 없는 값"
			+ "<h3>제목내용</h3>"
			+ "</div>"
			+ "<img src='localhost:8080/images/temporary/222d6b0e-818d-47ea-a9f5-cfac9dc0a242.png' style='height=240px; width=160px;' alt='세번째 사진.png' bytesize='22222'> 이상한 글귀<br>"
			+ "</div>";
	
	@Test
	void testExtractImageElement() {
		contentDocumentService.extractImageElement(contentBody);
	}

	@Test
	void testChangeImageSorce() {
		contentDocumentService.changeImageSorce(contentBody, "content");
	}

}
