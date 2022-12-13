package com.inhatc.spring.capstone.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.inhatc.spring.capstone.test.controller.projectcontroller.projectDto;

import lombok.Getter;
import lombok.Setter;

@Controller
public class recruimentcontroller {

	@Getter
	@Setter
	class recruimentDto{
		String title ="제목";//구인공고 이름
		int count = 0;//구인 현황 남은인원
		String ends="프론트 엔드";// 프론트 엔드 백엔드
		String usedLanguage = "자바";//언어
		String writer= "김상덕";//작성자
		String email="eeemail@mail.com";//이메일
		int viewCount=0;//조회수
		// 구인시현황을 리스트로 해야하는데 여기다가 리스트를 만들까요

		
		public recruimentDto(String title,String email,String writer,int viewCount
				) {
		this.title = title;
		this.writer =writer;
		this.email=email;
		this.viewCount = viewCount;

		}
	}
	
	@Getter
	@Setter
	class recruimentmemberDto{
		int count = 0;//구인 현황 남은인원
		String ends="프론트 엔드";// 프론트 엔드 백엔드
		String usedLanguage = "자바";//언어
		
		public recruimentmemberDto(String ends,String usedLanguage,int count) {
			this.ends = ends;
			this.usedLanguage=usedLanguage;
			this.count = count;
		}
	}
	
	@GetMapping("/recruimentboard")
	public String recruimentboard(Model model) {
		

		List<recruimentDto> recruimentList = new ArrayList<>();//프로젝트 리스트
		recruimentDto test1 = new recruimentDto("캡스톤 디자인관리", "email@mail.com","김상덕",76);
		recruimentDto test2 = new recruimentDto("이메일 관리", "1email@mail.com", "길드", 79);
		recruimentDto test3 = new recruimentDto("드론 프로젝트", "3email@mail.com", "라이트", 77);
		
		recruimentList.add(test1);
		recruimentList.add(test2);
		recruimentList.add(test3);
		
		List<recruimentmemberDto> recruimentmemberList = new ArrayList<>();//프로젝트 리스트
		recruimentmemberDto test11 = new recruimentmemberDto("프론트엔드","자바",3);
		recruimentmemberDto test12 = new recruimentmemberDto("백엔드","C",1);
		recruimentmemberDto test13 = new recruimentmemberDto("백엔드","C#",2);

		recruimentmemberList.add(test11);
		recruimentmemberList.add(test12);
		recruimentmemberList.add(test13);
	
		model.addAttribute("recruiment_member",recruimentmemberList);
		model.addAttribute("recruiment_",recruimentList);
		return "/recruimentboard";
	}

	
}
