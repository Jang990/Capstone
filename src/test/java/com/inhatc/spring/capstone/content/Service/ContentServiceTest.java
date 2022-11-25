package com.inhatc.spring.capstone.content.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.spring.capstone.content.dto.ContentDTO;
import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.content.repository.ContentRepository;
import com.inhatc.spring.capstone.user.dto.UsersJoinDTO;
import com.inhatc.spring.capstone.user.entity.Users;
import com.inhatc.spring.capstone.user.exception.UserErrorDescription;
import com.inhatc.spring.capstone.user.exception.UsersException;
import com.inhatc.spring.capstone.user.repository.UsersRepository;

@SpringBootTest
class ContentServiceTest {

	/*
	 * 현재 테스트는 파일입출력, recruit을 제외한 순수 Content 저장 테스트
	 * 나중에 다른 모듈 구현 후 재테스트 예정
	 */
	
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private ContentService contentService;
	
	// 사용자 생성
	Users createUser() {
		UsersJoinDTO userDto = new UsersJoinDTO("test@test.com", "홍길동", "testPassword");		
		return userRepository.save(Users.createUser(userDto));
	}
	
	// 가입되지 않은 사용자 생성
	Users createNonExistentUser() {
		Users user = mock(Users.class);
		when(user.getId()).thenReturn(Long.MIN_VALUE);
		when(user.getName()).thenReturn("가입안된사용자");
		when(user.getEmail()).thenReturn("test@test.com");
		return user;
	}
	
	// 게시글 DTO 생성
	ContentDTO createProjectContentDTO(Users user, String title) {
		String str = ""; // 게시글 내용
		for (int i = 0; i < 100; i++) {
			str += "테스트 게시물입니다" + (i * 31) + "\n";
		}
		
		return ContentDTO.builder()
				.userId(user.getId())
				.userEmail(user.getEmail())
				.title(title)
				.content(str)
				.usedLanguage("JAVA")
				.viewCount(0)
				.voteCount(0)
				.isRecruit(false)
				.build();
	}
	
	@Test
	@Transactional
	@DisplayName("단일 프로젝트 게시글 생성")
	void createSingleProjectContent() {
		Users user = createUser();
		ContentDTO contentDto = createProjectContentDTO(user, "테스트 게시물");
		
		ContentDTO createdContentDto = contentService.createProjectContent(contentDto);
		
		assertThat(contentDto).usingRecursiveComparison().isEqualTo(createdContentDto); 
	}
	
	@Test
	@Transactional
	@DisplayName("여러 프로젝트 게시글 생성")
	void createMultiProjectContent() {
		Users user = createUser();
		List<ContentDTO> contentDtoList = new ArrayList<>(); 
		for (int i = 0; i < 100; i++) {
			contentDtoList.add(createProjectContentDTO(user, "테스트 게시물 " + i));
		}
		
		List<ContentDTO> dbContentList = new ArrayList<>();
		for (int i = 0; i < contentDtoList.size(); i++) {
			dbContentList.add(contentService.createProjectContent(contentDtoList.get(i)));
		}
		
		for (int i = 0; i < contentDtoList.size(); i++) {
			assertThat(contentDtoList.get(i)).usingRecursiveComparison().isEqualTo(dbContentList.get(i)); 
		}
	}
	
	@Test
	@Transactional
	@DisplayName("존재하지 않는 사용자의 게시글 생성 시도")
	void createContentByNonExistentUser() {
		Users user = createNonExistentUser();
		ContentDTO contentDto = createProjectContentDTO(user, "테스트 게시물");
		
		UsersException e = assertThrows(UsersException.class, () -> {contentService.createProjectContent(contentDto);});
		
		assertEquals(UserErrorDescription.NOT_FOUND_USER, e.getErrorDescription()); 
	}
	
	@Test
	@Transactional
	@DisplayName("QueryDSL 테스트")
	void getContentViewTest() {
		createSingleProjectContent();
		contentService.viewProjectContent(null);
	}


}
