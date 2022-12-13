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
public class myPagecontroller2 {

	@Getter
	@Setter
	class myrecruimentDto{
		String title ="제목";//구인공고 이름
		int count = 0;//구인 현황 남은인원
		String ends="프론트 엔드";// 프론트 엔드 백엔드
		String usedLanguage = "자바";//언어
		String writer= "김상덕";//작성자
		String email="eeemail@mail.com";//이메일
		int viewCount=0;//조회수
		// 구인시현황을 리스트로 해야하는데 여기다가 리스트를 만들까요

		
		public myrecruimentDto(String title,String email,String writer,int viewCount
				) {
		this.title = title;
		this.writer =writer;
		this.email=email;
		this.viewCount = viewCount;

		}
	}
	
	@Getter
	@Setter
	class myrecruimentmemberDto{
		int count = 0;//구인 현황 남은인원
		String ends="프론트 엔드";// 프론트 엔드 백엔드
		String usedLanguage = "자바";//언어
		
		public myrecruimentmemberDto(String ends,String usedLanguage,int count) {
			this.ends = ends;
			this.usedLanguage=usedLanguage;
			this.count = count;
		}
	}
	
	@GetMapping("/myrecruimentboard")
	public String recruimentboard(Model model) {
		

		List<myrecruimentDto> myrecruimentList = new ArrayList<>();//프로젝트 리스트
		myrecruimentDto test1 = new myrecruimentDto("캡스톤 디자인관리", "email@mail.com","김상덕",76);
		myrecruimentDto test2 = new myrecruimentDto("이메일 관리", "1email@mail.com", "길드", 79);
		myrecruimentDto test3 = new myrecruimentDto("드론 프로젝트", "3email@mail.com", "라이트", 77);
		
		myrecruimentList.add(test1);
		myrecruimentList.add(test2);
		myrecruimentList.add(test3);
		
		List<myrecruimentmemberDto> myrecruimentmemberList = new ArrayList<>();//프로젝트 리스트
		myrecruimentmemberDto test11 = new myrecruimentmemberDto("프론트엔드","자바",3);
		myrecruimentmemberDto test12 = new myrecruimentmemberDto("백엔드","C",1);
		myrecruimentmemberDto test13 = new myrecruimentmemberDto("백엔드","C#",2);

		myrecruimentmemberList.add(test11);
		myrecruimentmemberList.add(test12);
		myrecruimentmemberList.add(test13);
	
		model.addAttribute("myrecruiment_member",myrecruimentmemberList);
		model.addAttribute("myrecruiment_",myrecruimentList);
		return "/myrecruimentboard";
	}

	
}
