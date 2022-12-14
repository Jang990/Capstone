package com.inhatc.spring.capstone.test.controller;


import java.io.IOException;



import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kohsuke.github.GHEventPayload.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.inhatc.spring.capstone.entity.board.board;
import com.inhatc.spring.capstone.test.service.boardservice;
import com.querydsl.core.util.FileUtils;

import groovy.util.logging.Log4j2;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.inhatc.spring.capstone.content.service.ContentService;
import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.entity.Content;

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
    public String boardView() {
    	return "boardview";
    }
    
    
	@PostMapping("editor/editor4")
	public String boardWritePro(NewContentDTO content, String tag){
		System.out.println(tag);
		System.out.println(content);
		System.out.println(content.getTitle());
		System.out.println(content.getContent());
		System.out.println(content.getUsedLanguage());
        content.setUserEmail("simbonggyo@gmail.com");
        content.setRecruit(false);
        try {
			contentService.createProjectContent(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/main";
	
	}
	
	
	
	}




