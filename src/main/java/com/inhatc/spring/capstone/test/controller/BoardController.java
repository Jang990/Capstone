package com.inhatc.spring.capstone.test.controller;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.inhatc.spring.capstone.content.dto.DisplayedCommentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedFileDTO;
import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.service.ContentService;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.test.service.boardservice;
import com.inhatc.spring.capstone.user.dto.DisplayedUserDTO;

@Controller

public class BoardController {
	
	
	
	
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private boardservice boardservice;
	
    @GetMapping("editor/editor3")
    public String editorboard() {
		
		return "editor/editor3";
	}
    
    @GetMapping("/boardview")
    public String boardView(Model model,Integer id) {
    	
    	model.addAttribute(boardservice);	
    	DisplayedUserDTO user = new DisplayedUserDTO("Sim", "sim@gmail.com");
        List<DisplayedFileDTO> files = new ArrayList<DisplayedFileDTO>();
        List<DisplayedCommentDTO> comments = new ArrayList<DisplayedCommentDTO>();
        List<DisplayedTagDTO> tags = new ArrayList<DisplayedTagDTO>();
        DisplayedContentDTO content = new DisplayedContentDTO(1L, user, "제목", "내용", LocalDateTime.now(), "Java", false, 3, comments, files, 3, tags);    	model.addAttribute("content", content);
    	return "/boardview";
    	
    	
     }
    
    
    
   
    
    
    
    
    
    
    
    
    
    
    
	@PostMapping("editor/editor4")
	public String boardWritePro(NewContentDTO content){
		
		System.out.println(content.getTitle());
		System.out.println(content.getContent());
		System.out.println(content.getUsedLanguage());
        content.setUserEmail("simbonggyo@gmail.com");
        content.setUserId(null);
        content.setRecruit(false);
		return "/main";
	
	}
	
	
	
	}




