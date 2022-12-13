package com.inhatc.spring.capstone.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.inhatc.spring.capstone.content.entity.UploadFile;
import com.inhatc.spring.capstone.content.service.ContentService;
@Controller

public class imagecontroller {

	
	
	 @PostMapping("/images/temporary")
	    @ResponseBody
	    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
	        try {
	            UploadFile uploadedFile = ContentService.createProjectContent(file);
	            return ResponseEntity.ok().body("/images/temporary/" + uploadedFile.getId());
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.badRequest().build();
	        }
	    }

	}
}



