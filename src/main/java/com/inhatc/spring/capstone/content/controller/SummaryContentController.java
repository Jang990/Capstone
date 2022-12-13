package com.inhatc.spring.capstone.content.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inhatc.spring.capstone.content.dto.DisplayedSummaryContentDTO;
import com.inhatc.spring.capstone.content.service.ContentSummaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/summary-content")
public class SummaryContentController {
	
	private final ContentSummaryService contentSummaryService;
	
	@GetMapping
	public List<DisplayedSummaryContentDTO> getContentList(@PageableDefault(page = 0, size = 6) Pageable pageable) {
		
		return null;
	}
}
