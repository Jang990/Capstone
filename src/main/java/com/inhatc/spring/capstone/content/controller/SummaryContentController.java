package com.inhatc.spring.capstone.content.controller;

import java.util.List; 

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inhatc.spring.capstone.content.dto.DisplayedSummaryContentDTO;
import com.inhatc.spring.capstone.content.service.ContentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/summary-content")
public class SummaryContentController {
	
	private final ContentService contentService;
	
	@GetMapping
	public List<DisplayedSummaryContentDTO> getContentList(@PageableDefault(page = 0, size = 6) 
		@SortDefault.SortDefaults({ @SortDefault(sort = "date_created", direction = Sort.Direction.DESC)}) Pageable pageable,
		 @RequestParam(value = "search", required = false) String search) {
		if(search == null) {
			contentService.getSummaryContents(pageable,null,null);
		}
		else {
			// 일단 검색기능은 컨트롤러 만들고 설정하기
		}
		return null;
	}
}
