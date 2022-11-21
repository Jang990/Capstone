package com.inhatc.spring.capstone.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
	@GetMapping("/login")
	/** 
	 * 로그인 페이지
	 * 일단oauth 테스트 페이지로 이동
	 * 프론트 작업 끝나면 변경할 것 
	 * */
	public String oauthTest() {
		return "/test/oauth/OauthTest";
	}
}
