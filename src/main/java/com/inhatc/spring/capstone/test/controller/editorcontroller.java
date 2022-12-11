package com.inhatc.spring.capstone.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import groovy.util.logging.Log4j2;

@Controller
public class editorcontroller {
	
	@GetMapping("/editor/editor3")
	public String editorboard() {
		
		return "/editor/editor3";
	}

}
