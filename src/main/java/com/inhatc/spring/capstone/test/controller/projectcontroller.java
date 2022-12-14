package com.inhatc.spring.capstone.test.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedSummaryContentDTO;
import com.inhatc.spring.capstone.content.service.ContentService;


import groovy.util.logging.Log4j2;
import javassist.expr.NewArray;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Controller
@RequiredArgsConstructor
public class projectcontroller {
	
	private final ContentService contentService;
	
	@Getter
	@Setter
	class projectDto{
		String title ="제목";//프로젝트 이름
		String accessDate = "2022-12-12";//작성시간
		String usedLanguage="JAVA";//언어
		String writer= "김상덕";//작성자
		String email="";//이메일
		int viewCount=0;//조회수
		
		
		public projectDto(String title,String email,String writer,String accessDate,String usedLanguage,int viewCount) {
		this.title = title;
		this.writer =writer;
		this.email=email;
		this.accessDate = accessDate;
		this.usedLanguage = usedLanguage;
		this.viewCount = viewCount;
		}
	}
	
	@GetMapping("/projectboard")

	// public String projectboard(Model model) {
	// 	Long bb = null;
	// 	List<DisplayedTagDTO> tag = new ArrayList<>();
	// 	DisplayedTagDTO a = new DisplayedTagDTO(bb,"java","Tech");
	// 	tag.add(a);
		
	// 	List<DisplayedSummaryContentDTO> projectList = new ArrayList<>();//프로젝트 리스트
	// 	DisplayedSummaryContentDTO test1 = new DisplayedSummaryContentDTO(bb,"이메일관리 ","in",tag,"김씨","mail.comn",3,50,LocalDateTime.now());
	// 	DisplayedSummaryContentDTO test2 = new DisplayedSummaryContentDTO(bb,"이메일관리 ","in",tag,"김씨","mail.comn",3,50,LocalDateTime.now());
	// 	DisplayedSummaryContentDTO test3 = new DisplayedSummaryContentDTO(bb,"이메일관리 ","in",tag,"김씨","mail.comn",3,50,LocalDateTime.now());
	// 	DisplayedSummaryContentDTO test4 = new DisplayedSummaryContentDTO(bb,"이메일관리 ","in",tag,"김씨","mail.comn",3,50,LocalDateTime.now());
	// 	DisplayedSummaryContentDTO test5 = new DisplayedSummaryContentDTO(bb,"이메일관리 ","in",tag,"김씨","mail.comn",3,50,LocalDateTime.now());
		

	public String projectboard(Model model,
			@PageableDefault(page = 0, size = 6) 
			@SortDefault.SortDefaults({ @SortDefault(sort = "heart", direction = Sort.Direction.DESC)}) 
			Pageable pageable,
			@RequestParam(value = "keywords", required = false, defaultValue = "") String keywords,
			@RequestParam(value = "email", required = false, defaultValue = "") String email
	) {
		Page<DisplayedSummaryContentDTO> summaryContents = contentService.getSummaryContents(pageable, keywords, email);
		model.addAttribute("summaryDto", summaryContents); // 정보
		model.addAttribute("keywords", keywords); // 검색어 
		
		List<projectDto> projectList = new ArrayList<>();//프로젝트 리스트
		projectDto test1 = new projectDto("캡스톤 디자인관리", "email@mail.com", "김상덕", "2012-12-12", "자바", 76);
		projectDto test2 = new projectDto("이메일 관리", "1email@mail.com", "길드", "2012-12-17", "python", 79);
		projectDto test3 = new projectDto("드론 프로젝트", "3email@mail.com", "라이트", "2012-12-14", "C", 77);

		
		projectList.add(test1);
		projectList.add(test2);
		projectList.add(test3);
		projectList.add(test4);
		projectList.add(test5);
		
		
		model.addAttribute("project_",projectList);
		return "/projectboard";
	}
	@GetMapping("/projectboard_t")
	public String projectboard2(Model model) {
		Long bb = null;
		List<DisplayedTagDTO> tag = new ArrayList<>();
		DisplayedTagDTO a = new DisplayedTagDTO(bb,"java","Tech");
		tag.add(a);
		
		List<DisplayedSummaryContentDTO> projectList = new ArrayList<>();//프로젝트 리스트
		DisplayedSummaryContentDTO test1 = new DisplayedSummaryContentDTO(bb,"이메일관리 ","in",tag,"김씨","mail.comn",3,50,LocalDateTime.now());
		DisplayedSummaryContentDTO test2 = new DisplayedSummaryContentDTO(bb,"이메일관리 ","in",tag,"김씨","mail.comn",3,50,LocalDateTime.now());
		DisplayedSummaryContentDTO test3 = new DisplayedSummaryContentDTO(bb,"이메일관리 ","in",tag,"김씨","mail.comn",3,50,LocalDateTime.now());
		DisplayedSummaryContentDTO test4 = new DisplayedSummaryContentDTO(bb,"이메일관리 ","in",tag,"김씨","mail.comn",3,50,LocalDateTime.now());
		DisplayedSummaryContentDTO test5 = new DisplayedSummaryContentDTO(bb,"이메일관리 ","in",tag,"김씨","mail.comn",3,50,LocalDateTime.now());
		
		
		projectList.add(test1);
		projectList.add(test2);
		projectList.add(test3);
		projectList.add(test4);
		projectList.add(test5);
		
		model.addAttribute("project_",projectList);
		return "/projectboard_t";
	}
	
}
