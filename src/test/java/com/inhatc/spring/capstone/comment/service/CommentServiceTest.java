package com.inhatc.spring.capstone.comment.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.repository.ContentRepository;
import com.inhatc.spring.capstone.content.service.ContentService;
import com.inhatc.spring.capstone.user.dto.UsersJoinDTO;
import com.inhatc.spring.capstone.user.entity.Users;
import com.inhatc.spring.capstone.user.repository.UsersRepository;

@SpringBootTest
class CommentServiceTest {
	
	@Autowired
	private UsersRepository userRepository;
	@Autowired
	private ContentRepository contentRepository;
	@Autowired
	private ContentService contentService;
	
	@Test
	@Transactional
	@DisplayName("댓글 달기")
	void testRegisterComment() throws IOException {
		Users createUser = createUser();
		NewContentDTO contentDTO = createContentDTO(createUser, "테스트");
		DisplayedContentDTO projectContent = contentService.createProjectContent(contentDTO);
	}

	@Test
	@Transactional
	@DisplayName("댓글 삭제")
	void testDeleteComment() {
		fail("Not yet implemented");
	}

	@Test
	@Transactional
	@DisplayName("댓글 수정")
	void testModifyComment() {
		fail("Not yet implemented");
	}

	@Test
	@Transactional
	@DisplayName("사용자의 댓글 삭제 서비스")
	void testUserDeleteService() {
		fail("Not yet implemented");
	}

	@Test
	@Transactional
	@DisplayName("댓글 수정 서비스")
	void testModifyCommentService() {
		fail("Not yet implemented");
	}
	
	
	/** 사용자 생성 */
	Users createUser() {
		UsersJoinDTO userDto = new UsersJoinDTO("test@test.com", "홍길동", "testPassword");		
		return userRepository.save(Users.createUser(userDto));
	}
	
	/** 게시글 DTO 생성 */
	NewContentDTO createContentDTO(Users user, String title) {
		String str = ""; // 게시글 내용
		str += "<div>";
		for (int i = 0; i < 5; i++) {
			str += "테스트 게시물입니다" + (i * 31) + "\n";
		}
		str += "</div>";
		
		return NewContentDTO.builder()
				.userId(user.getId())
				.userEmail(user.getEmail())
				.title(title)
				.content(str)
				.usedLanguage("JAVA")
				.isRecruit(false)
				.build();
	}

}
