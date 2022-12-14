package com.inhatc.spring.capstone.test.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inhatc.spring.capstone.content.dto.DisplayedSummaryContentDTO;
import com.inhatc.spring.capstone.content.repository.ContentRepository;

@RestController
@RequestMapping("/api/test")
public class SearchTestController {
	
	@Autowired
	ContentRepository contentRepository;
	
	@GetMapping
	public List<DisplayedSummaryContentDTO> test(
			@PageableDefault(page = 0, size = 1) 
			@SortDefault.SortDefaults({ @SortDefault(sort = "heart", direction = Sort.Direction.DESC)}) 
			Pageable pageable,
			@RequestParam(value = "keywords", required = false, defaultValue = "") String keywords,
			@RequestParam(value = "email", required = false, defaultValue = "") String email
			 
			,
			 @RequestParam(value = "search", required = false) String search
	) {
		List<String> keywordList;
		if(keywords == null)
			keywordList = new ArrayList<>();
		else
			keywordList = Arrays.asList(keywords.split(" "));
		
		System.out.println("요기도");
		for (String string : keywordList) {
			System.out.println(string);
		}
		
		System.out.println("====================");
		System.out.println(keywords);
		System.out.println(email);
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getOffset());
		System.out.println(pageable.getPageSize());
		System.out.println("====================");
	
		
		Page<DisplayedSummaryContentDTO> page = contentRepository.getSummaryContentPage(pageable, keywordList, email);
		System.out.println("====================");
		System.out.println(page.getTotalPages()); // 페이지 수
		System.out.println(page.getTotalElements()); // 총 요소의 수
		System.out.println(page.getNumberOfElements()); // 현재 페이지
		System.out.println(page.getNumber()); // 현재 몇 페이지인지
		System.out.println("====================");
		
		
		return page.getContent();
	}
}
