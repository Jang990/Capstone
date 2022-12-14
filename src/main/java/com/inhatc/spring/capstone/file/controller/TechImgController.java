package com.inhatc.spring.capstone.file.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inhatc.spring.capstone.file.service.TechImgService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tech-tag")
public class TechImgController {
	
	private final TechImgService techImgService;
	
	/** Tech 태그에 해당하는 리소스 위치 가져오기 */
	@GetMapping("/resource-loc/{tagId}")
	public ResponseEntity<String> getTechTagResourceLocation(@PathVariable Long tagId) {
		String path = null;
		try {
			path = techImgService.getTechImgResourceSrc(tagId);
		}
		catch (IOException e) {
			// 파일 불러오기 실패 - 로그 남겨야 함
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(path ,HttpStatus.OK);
	}
}
