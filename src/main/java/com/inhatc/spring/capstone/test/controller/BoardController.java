package com.inhatc.spring.capstone.test.controller;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import java.io.IOException; 




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import com.inhatc.spring.capstone.content.dto.DisplayedCommentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedFileDTO;
import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.service.ContentService;
import com.inhatc.spring.capstone.tag.constant.TagType;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.user.dto.DisplayedUserDTO;


@Controller

public class BoardController {
	
	
	
	
	
	@Autowired
	private ContentService contentService;
	
    @GetMapping("editor/editor3")
    public String editorboard() {
		
		return "editor/editor3";
	}
    
    @GetMapping("/boardview")
    public String boardView(Model model,Integer id) {
    	
       	DisplayedUserDTO user = new DisplayedUserDTO("Sim", "sim@gmail.com");
        List<DisplayedFileDTO> files = new ArrayList<DisplayedFileDTO>();
        List<DisplayedCommentDTO> comments = new ArrayList<DisplayedCommentDTO>();
        List<DisplayedTagDTO> tags = new ArrayList<DisplayedTagDTO>();
        DisplayedContentDTO content = new DisplayedContentDTO(1L, user, "제목", "내용", LocalDateTime.now(), "Java", false, 3, comments, files, 3, tags);    	model.addAttribute("content", content);
    	return "/boardview";
    	
    	
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
        
        DisplayedContentDTO createProjectContent = null;
        try {
			createProjectContent = contentService.createProjectContent(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        if(createProjectContent == null) {
        	return "/main"; // 에러페이지로 이동
        }
        else {
        	return "redirect:/projectboard/" + createProjectContent.getContentId();
        }
	
	}
	
	}




