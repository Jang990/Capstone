package com.inhatc.spring.capstone.content.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.content.entity.ContentHeart;
import com.inhatc.spring.capstone.content.repository.ContentHeartRepository;
import com.inhatc.spring.capstone.content.repository.ContentRepository;
import com.inhatc.spring.capstone.user.entity.Users;
import com.inhatc.spring.capstone.user.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
/** 컨텐츠에 하트버튼을 눌렀을 때 일어나는 이벤트 서비스 */
public class ContentHeartService {
	
	private final ContentHeartRepository heartRepository;
	private final UsersRepository userRepository;
	private final ContentRepository contentRepository;
	
	/** 좋아요 누름 */
	public void likeContent(Long contentId, String email) {
		Content content = contentRepository.findById(contentId).orElseThrow(EntityNotFoundException::new);
		Users user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
		
		ContentHeart contentHeart = new ContentHeart(content, user);
		heartRepository.save(contentHeart);
	}
	
	/** 좋아요 취소함 */
	public void dislikeContent(Long contentId, String email) {
		ContentHeart contentHeart = heartRepository.findByContent_IdAndLikedUser_Email(contentId, email)
				.orElseThrow(EntityNotFoundException::new);
		heartRepository.delete(contentHeart);
	}
}
