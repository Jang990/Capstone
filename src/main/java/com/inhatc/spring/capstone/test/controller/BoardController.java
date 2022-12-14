package com.inhatc.spring.capstone.test.controller;


import java.io.IOException;



import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kohsuke.github.GHEventPayload.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.inhatc.spring.capstone.entity.board.board;
import com.inhatc.spring.capstone.tag.constant.TagType;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
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
        
        
//        /*
        try {
			contentService.createProjectContent(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
//        */
		return "/main";
	
	}
	
	
	
	}




