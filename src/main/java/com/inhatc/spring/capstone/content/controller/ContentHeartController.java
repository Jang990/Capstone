package com.inhatc.spring.capstone.content.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
/** 사용자가 컨텐츠에 있는 하트를 누를 때 들이오는 Rest 컨트롤러 */
public class ContentHeartController {
	
	@GetMapping("/heart")
	public boolean likeContent(Authentication authentication) {
		OAuth2User user = (OAuth2User) authentication.getPrincipal();
		System.out.println("회원정보==================");
		Map<String, Object> attributes = user.getAttributes();
		attributes.forEach((k,v) -> System.out.println(k + ": " + v));
		System.out.println("==========================");
		return true;
	}
}
