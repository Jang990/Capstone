package com.inhatc.spring.capstone.test.controller;

import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inhatc.spring.capstone.test.dto.ImgLocTestDTO;
import com.inhatc.spring.capstone.test.dto.TestDTO;
import com.inhatc.spring.capstone.test.service.TestService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
	
	private final TestService testService;
	
	@GetMapping("")
	public void test(Model model) {
		TestDTO testObj = testService.getTest(100L);
		model.addAttribute("testObj", testObj);
	}
	
	@GetMapping("/icon")
	public void test2(Model model) {
		String java = "JAVA";
		ImgLocTestDTO img = new ImgLocTestDTO(java.toLowerCase());
		
		// wordmark 사진이 있을 수도 있고 없을 수도 있음. 파일체크를 해봐야 함
		model.addAttribute("imgLocData", img);
	}
}
