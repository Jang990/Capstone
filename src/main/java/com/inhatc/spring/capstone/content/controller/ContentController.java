package com.inhatc.spring.capstone.content.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedSummaryContentDTO;
import com.inhatc.spring.capstone.content.service.ContentDocumentService;
import com.inhatc.spring.capstone.content.service.ContentService;
import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;
import com.inhatc.spring.capstone.file.service.TemporaryImageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projectboard")
public class ContentController {
	
	private final ContentService contentService;
	
	@GetMapping
	public String projectboard(Model model,
			@PageableDefault(page = 0, size = 6) 
			@SortDefault.SortDefaults({ @SortDefault(sort = "heart", direction = Sort.Direction.DESC)}) 
			Pageable pageable,
			@RequestParam(value = "keywords", required = false, defaultValue = "") String keywords,
			@RequestParam(value = "email", required = false, defaultValue = "") String email
	) {
		Page<DisplayedSummaryContentDTO> summaryContents = contentService.getSummaryContents(pageable, keywords, email);
		model.addAttribute("project_", summaryContents); // 정보
		model.addAttribute("keywords", keywords); // 검색어 
		model.addAttribute("maxPage", 5);
		
		return "/projectboard";
	}
	
	/** 프로젝트 컨텐츠를 클릭해서 페이지 이동 - 연결 */
	@GetMapping("/{contentId}")
	public String viewContent(Model model, @PathVariable("contentId")Long contentId) {
		// Cookie를 이용한 조회수 증가 확인 - 추후 구현
		DisplayedContentDTO viewProjectContent = contentService.viewProjectContent(contentId);
		System.out.println(viewProjectContent);
		model.addAttribute("content",viewProjectContent);
		return "/boardview";
	}
	
	
	/** 프로젝트 리스트 페이지에서 새로만들기 같은 create 버튼 클릭 */
	@PostMapping
	@ResponseBody
	public String createContent(NewContentDTO contentDto) {
		if(contentDto.isRecruit()) {
			// 프로젝트 구인 게시글 생성 - 미구현
			contentService.createRecruitContent(contentDto);
		}
		else {
			// 프로젝트 게시글 생성
			try {
				contentService.createProjectContent(contentDto);
			} catch (IOException e) {
				return "임시저장폴더 -> 실제 저장폴더로 이미지 파일 이동 실패";
			}
		}
		return "완료";
	}
	
	/** 프로젝트 수정 버튼 클릭 */
	@PutMapping("/{contentId}")
	@ResponseBody
	public String modifyProjectContent(NewContentDTO contentDto, @PathVariable("contentId")Long contentId) {
		// 컨트롤러에서 수정할 권한이 있는지 확인? - 미완료
		try {
			contentService.modifyProjectContent(contentDto);
		} catch (IOException e) {
			return "임시저장폴더 -> 실제 저장폴더로 이미지 파일 이동 실패";
		}
		
		// Model로 업데이트한 콘텐츠 보내기
		contentService.viewProjectContent(contentId);
		return "부분 완료";
	}
	
	
}
