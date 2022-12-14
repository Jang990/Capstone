package com.inhatc.spring.capstone.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.inhatc.spring.capstone.content.dto.DisplayedCommentDTO;

@Controller
public class commentcontroller {

		
	@RequestMapping(value = "/boardview", method = RequestMethod.POST)
	public String posttWirte(DisplayedCommentDTO comment) throws Exception {
		
		System.out.println(comment.getComment());
		
		return "/boardview";
		//return "redirect:/board/view?bno=" + comment.getCommentId();
	}
}
