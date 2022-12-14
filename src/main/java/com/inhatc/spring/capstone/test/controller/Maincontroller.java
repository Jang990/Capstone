package com.inhatc.spring.capstone.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Maincontroller {
	
	private final TagService tagService;
	
	@GetMapping
	public String main(Model model) {
			String tag1 = "자바";//태그 1위 가장많이 적힌 태그 순위
			String tag2 = "python";//태그 2위
			String tag3 = "C";//태그 3위
			String tag4 = "C++";//태그 4위
			String tag5 = "Linux";//태그 5위
			
			List<String> tag = new ArrayList<>();//태그 리스트
			tag.add(tag1);
			tag.add(tag2);
			tag.add(tag3);
			tag.add(tag4);
			tag.add(tag5);
//			model.addAttribute("tags", tag);
		
			
			List<DisplayedTagDTO> Top5_Tags = tagService.searchTop5Tags();
			model.addAttribute("tags",Top5_Tags);
			
		return "main";
	}
	
	
	@GetMapping("test/search_action")
	public String search(String searchtxt) {
		System.out.println(searchtxt);// 검색어
		
		return "main";
	}
	
}
