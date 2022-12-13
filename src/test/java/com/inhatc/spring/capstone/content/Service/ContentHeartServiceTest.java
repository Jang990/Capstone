package com.inhatc.spring.capstone.content.Service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.content.entity.ContentHeart;
import com.inhatc.spring.capstone.content.repository.ContentHeartRepository;
import com.inhatc.spring.capstone.content.repository.ContentRepository;
import com.inhatc.spring.capstone.content.service.ContentHeartService;
import com.inhatc.spring.capstone.user.dto.UsersJoinDTO;
import com.inhatc.spring.capstone.user.entity.Users;
import com.inhatc.spring.capstone.user.repository.UsersRepository;

@SpringBootTest
class ContentHeartServiceTest {

	@Autowired
	ContentRepository contentRepository;
	@Autowired
	ContentHeartRepository heartRepository;
	@Autowired
	ContentHeartService heartService;
	@Autowired
	UsersRepository userRepository;

	// 사용자 생성
	Users createUser() {
		UsersJoinDTO userDto = new UsersJoinDTO("test@test.com", "홍길동", "testPassword");
		return userRepository.save(Users.createUser(userDto));
	}

	// 게시글 DTO 생성
	NewContentDTO createProjectContentDTO(Users user, String title) {
		String str = ""; // 게시글 내용
		str += "<div>";
		for (int i = 0; i < 5; i++) {
			str += "테스트 게시물입니다" + (i * 31) + "\n";
		}
		str += "</div>";

		return NewContentDTO.builder().userId(user.getId()).userEmail(user.getEmail()).title(title).content(str)
				.usedLanguage("JAVA").isRecruit(false).build();
	}

	@Test
	@Transactional
	@DisplayName("좋아요 누르기 테스트")
	public void likeContentTest() {
		Users createdUser = createUser();
		NewContentDTO craetedContent = createProjectContentDTO(createdUser, "테스트 게시글");
		Content content = Content.createContent(createdUser, craetedContent);
		content = contentRepository.save(content);
		
		heartService.likeContent(content.getId(), createdUser.getEmail());
		
		ContentHeart heart = heartRepository.findByContent_IdAndLikedUser_Email(content.getId(), createdUser.getEmail()).get();
		assertEquals(heart.getLikedUser(), createdUser);
		assertEquals(heart.getContent(), content);
		assertEquals(content.getHeartCount(), 1);
	}

	@Test
	@Transactional
	@DisplayName("좋아요 취소 테스트")
	public void dislikeContentTest() {
		Users createdUser = createUser();
		NewContentDTO craetedContent = createProjectContentDTO(createdUser, "테스트 게시글");
		Content content = Content.createContent(createdUser, craetedContent);
		content = contentRepository.save(content);
		heartService.likeContent(content.getId(), createdUser.getEmail());
		
		heartService.dislikeContent(content.getId(), createdUser.getEmail());
		
		Long contentId = content.getId();
		String email = createdUser.getEmail();
		assertThrows(NoSuchElementException.class , () -> {heartRepository.findByContent_IdAndLikedUser_Email(contentId, email).get();});
		assertEquals(content.getHeartCount(), 0);
	}
}
