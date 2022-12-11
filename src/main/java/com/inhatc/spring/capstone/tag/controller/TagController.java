package com.inhatc.spring.capstone.tag.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/api/tag")
public class TagController {
	
	/* 사용자 정의 태그 생성은 Content를 작성할 때 일단 만들어두면 Content를 저장할 때 생성하면 된다. */
	
	@GetMapping("/similar/{tag}")
	/** 현재 태그와 유사한 태그를 10개 검색해서 보내준다. */
	public String craeteCustomTag(@PathVariable String tag) {
		List<String> similarTagList = new ArrayList<>();
		return null;
	}
}
