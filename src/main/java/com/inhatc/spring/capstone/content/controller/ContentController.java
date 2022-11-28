package com.inhatc.spring.capstone.content.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.service.ContentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/content")
public class ContentController {
	
	private final ContentService contentService;
	
	/** 프로젝트 리스트 페이지에서 새로만들기 같은 create 버튼 클릭 */
	@PostMapping
	public String createContent(NewContentDTO contentDto) {
		
		if(contentDto.isRecruit()) {
			// 프로젝트 구인 게시글 생성 - 미구현
			contentService.createRecruitContent(contentDto);
		}
		else {
			// 프로젝트 게시글 생성
			contentService.createProjectContent(contentDto);
		}
		return "완료";
	}
	
	/** 프로젝트 컨텐츠를 클릭해서 페이지 이동 */
	@GetMapping("/{contentId}")
	public String viewContent(@PathVariable("contentId")Long contentId) {
		// Cookie를 이용한 조회수 증가 확인 - 추후 구현
		contentService.viewProjectContent(contentId);
		return "";
	}
	
	/** 프로젝트 수정 버튼 클릭 */
	@PutMapping("/{contentId}")
	public String modifyProjectContent(NewContentDTO contentDto, @PathVariable("contentId")Long contentId) {
		// 컨트롤러에서 수정할 권한이 있는지 확인? - 미완료
		contentService.modifyProjectContent(contentDto);
		
		// Model로 업데이트한 콘텐츠 보내기
		contentService.viewProjectContent(contentId);
		return "부분 완료";
	}
	
	
}
