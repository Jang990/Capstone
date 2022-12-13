package com.inhatc.spring.capstone.content.controller;

import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inhatc.spring.capstone.content.service.ContentHeartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
/** 사용자가 컨텐츠에 있는 하트를 누를 때 들이오는 Rest 컨트롤러 */
public class ContentHeartController {
	
	private final ContentHeartService heartService;
	
	/** 비어 있는 하트를 눌렀을 때 */
	@GetMapping("/like/{contentId}")
	public ResponseEntity<Boolean> likeContent(Authentication authentication, @PathVariable Long contentId) {
		OAuth2User user = (OAuth2User) authentication.getPrincipal();
		Map<String, Object> attributes = user.getAttributes();
		try {
			heartService.likeContent(contentId, (String)attributes.get("email"));
		}
		catch(EntityNotFoundException e) {
			// 해당 컨텐츠 또는 사용자를 찾을 수 없음
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			// 이외의 오류
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
	/** 이미 하트를 누른 컨텐츠에 하트를 눌러 취소할 때 */
	@GetMapping("/dislike/{contentId}")
	public ResponseEntity<Boolean> dislikeContennt(Authentication authentication, @PathVariable Long contentId) {
		OAuth2User user = (OAuth2User) authentication.getPrincipal();
		Map<String, Object> attributes = user.getAttributes();
		
		try {
			heartService.dislikeContent(contentId, (String)attributes.get("email"));
		}
		catch(EntityExistsException e) {
			// 해당 게시글에 좋아요를 누른 적 없음
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}
		catch(Exception e) {
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
	
}
