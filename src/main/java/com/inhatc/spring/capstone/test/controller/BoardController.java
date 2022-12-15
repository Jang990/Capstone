package com.inhatc.spring.capstone.test.controller;


import java.io.IOException; 

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.inhatc.spring.capstone.tag.constant.TagType;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;

import com.inhatc.spring.capstone.content.service.ContentService;
import com.inhatc.spring.capstone.content.dto.NewContentDTO;

@Controller

public class BoardController {
	
	@Autowired
	private ContentService contentService;
	
    @GetMapping("editor/editor3")
    public String editorboard() {
		
		return "editor/editor3";
	}
    
    @GetMapping("/boardview")
    public String boardView() {
    	return "boardview";
    }
    
    
	@PostMapping("editor/editor4")
	public String boardWritePro(Authentication authentication, NewContentDTO content, String tag, Long usedLangId){
		OAuth2User user = (OAuth2User) authentication.getPrincipal();
        content.setUserEmail((String)user.getAttributes().get("email"));
        content.setRecruit(false);
        List<DisplayedTagDTO> tagList = new ArrayList<>();
        tagList.add(new DisplayedTagDTO(usedLangId, content.getUsedLanguage().toLowerCase(), TagType.TECH.toString()));
        String[] tags = tag.split(" ");
        for (String string : tags) {
        	tagList.add(new DisplayedTagDTO(null, string.trim(), TagType.UNKNOWN.toString()));
		}
        content.setTags(tagList);
        
        
        try {
			contentService.createProjectContent(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/main";
	
	}
	
	
	
	}




