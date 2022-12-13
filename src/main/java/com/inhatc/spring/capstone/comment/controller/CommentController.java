package com.inhatc.spring.capstone.comment.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inhatc.spring.capstone.comment.dto.NewCommentDTO;
import com.inhatc.spring.capstone.comment.service.CommentService;
import com.inhatc.spring.capstone.user.constant.Roles;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/content/{contentId}/comment")
public class CommentController {
	private final CommentService commentService;
	
	/** 댓글 등록 */
	@PostMapping
	public String registerComment(Authentication authentication, @PathVariable Long contentId, NewCommentDTO newComment) {
		OAuth2User user = (OAuth2User) authentication.getPrincipal();
		String userEmail = (String) user.getAttributes().get("email");
		
		commentService.registerComment(userEmail, contentId, newComment);
		
		return "다시 컨텐츠 페이지로 이동하면서 DB내용을 불러옴";
	}
	
	/** 댓글 삭제 */
	@PostMapping("/delete/{commentId}")
	public String deleteComment(Authentication authentication, @PathVariable Long commentId) {
		OAuth2User user = (OAuth2User) authentication.getPrincipal();
		String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
		
		if(role.equals(Roles.USER.toString())) {
			// 일반 사용자
			String userEmail = (String) user.getAttributes().get("email");
			try {
				commentService.userDeleteService(userEmail ,commentId); 
			}
			catch(Exception e) {
				return "USER 권한의 사용자는 자신의 댓글이 아니면 댓글 삭제 실패";
			}
		}
		else if(role.equals(Roles.ADMIN.toString())) {
			// admin
			commentService.deleteComment(commentId); 
		}
		
		return "다시 컨텐츠 페이지로 이동하면서 DB내용을 불러옴";
	}
	
	/** 댓글 수정 */
	@PostMapping("/modifiy/{commentId}")
	public String modifyComment(Authentication authentication, @PathVariable Long commentId, NewCommentDTO newComment) {
		// 운영자는 삭제만 할 수 있고 수정은 할 수 없기 때문에 일반 사용자와 차별을 두지 않는다.
		OAuth2User user = (OAuth2User) authentication.getPrincipal();
		String userEmail = (String) user.getAttributes().get("email");
		commentService.modifyCommentService(commentId, userEmail, newComment);
		
		return "다시 컨텐츠 페이지로 이동하면서 DB내용을 불러옴";
	}
}
