package com.inhatc.spring.capstone.test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inhatc.spring.capstone.entity.board.board;
import com.inhatc.spring.capstone.test.service.boardservice;

import groovy.util.logging.Log4j2;

@Controller

public class BoardController {
	
	@Autowired
	private boardservice boardservice;
	
	@GetMapping("test/editor3")
	public String editorboard() {
		
		return "editor/editor3";
	}
	@PostMapping("/test/editor4")
	public String boardWritePro(board board){
		
		//boardservice.write(board);
		System.out.println(board.getContent());

		return "";
		
	}

}
