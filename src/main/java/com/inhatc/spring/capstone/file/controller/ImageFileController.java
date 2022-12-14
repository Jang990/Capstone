package com.inhatc.spring.capstone.file.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;
import com.inhatc.spring.capstone.file.service.TemporaryImageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test") // 나중에 실제 적용시에는 떼버릴 것
public class ImageFileController {
	
	private final TemporaryImageService tempImageService;
	
	/**
	 * 글(Content) 등록 이전에 이미지를 추가했을 시 서버에 이미지 파일을 임시저장한다.
	 * 해당 컨트롤러는 임시저장을 위한 컨트롤러이다.
	 */
	@ResponseBody
    @RequestMapping(value="/images/temporary")
	public ResponseEntity<DisplayedImageDTO> saveTempImg(MultipartFile img) {
		if(img == null || img.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		DisplayedImageDTO imgDTO = null;
		try {
			imgDTO = tempImageService.saveTemporaryImage(img);
		} catch (IOException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(imgDTO, HttpStatus.OK);
	}
	
}
