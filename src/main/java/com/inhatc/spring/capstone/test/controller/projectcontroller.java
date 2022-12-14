package com.inhatc.spring.capstone.test.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedSummaryContentDTO;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;

import groovy.util.logging.Log4j2;
import javassist.expr.NewArray;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Controller
public class projectcontroller {
	
	
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
	public String projectboard(Model model) {
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
