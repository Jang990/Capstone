package com.inhatc.spring.capstone.test.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;


import com.inhatc.spring.capstone.content.dto.DisplayedCommentDTO;

@Controller
public class commentcontroller {

		
	@PostMapping("/editor/editor5")
	public String posttWirte(DisplayedCommentDTO comment) throws Exception {
		
		System.out.println(comment.getComment());
		
		return "/boardview";
		//return "redirect:/board/view?bno=" + comment.getCommentId();
	}
}
