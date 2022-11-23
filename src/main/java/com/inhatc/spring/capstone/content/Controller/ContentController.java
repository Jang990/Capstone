package com.inhatc.spring.capstone.content.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inhatc.spring.capstone.content.Service.ContentService;
import com.inhatc.spring.capstone.content.dto.ContentDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/content")
public class ContentController {
	
	private final ContentService contentService;
	
	@PostMapping
	public String createContent(ContentDTO contentDto) {
		if(contentDto.isRecruit()) {
			// 프로젝트 구인 게시글 생성
			contentService.createRecruitContent(contentDto);
		}
		else {
			// 프로젝트 게시글 생성
			contentService.createProjectContent(contentDto);
		}
		return "완료";
	}
}
