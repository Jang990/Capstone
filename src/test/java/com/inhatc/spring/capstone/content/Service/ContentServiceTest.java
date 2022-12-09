package com.inhatc.spring.capstone.content.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.content.repository.ContentRepository;
import com.inhatc.spring.capstone.content.service.ContentService;
import com.inhatc.spring.capstone.file.service.TemporaryImageService;
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
//	@Autowired
//	private TemporaryImageService tempImageService;
	
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
	NewContentDTO createProjectContentDTO(Users user, String title) throws IOException {
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
	
	// 단일 프로젝트 생성
	DisplayedContentDTO createSingleProjectContent() throws IOException {
		Users user = createUser();
		NewContentDTO contentDto = createProjectContentDTO(user, "테스트 게시물");
		
		DisplayedContentDTO createdContentDto = contentService.createProjectContent(contentDto);
		
		checkEqual(contentDto, createdContentDto);
		return createdContentDto;
	}
	
	@Test
	@Transactional
	@DisplayName("이미지가 없는 단일 프로젝트 게시글 생성")
	void createSingleProjectContentTest() throws IOException {
		Users user = createUser();
		NewContentDTO contentDto = createProjectContentDTO(user, "테스트 게시물");
		
		// 이미지가 있는경우 이렇게 테스트하면 되지만 나중에 테스트시에 해당 파일이 없으면 테스트 실패 - 따로 단위테스트를 해야할 것  같다.
//		NewContentDTO contentDto = createContentWithImage(user, "테스트 게시물");
		
		DisplayedContentDTO createdContentDto = contentService.createProjectContent(contentDto);
		
		checkEqual(contentDto, createdContentDto);
	}
	
	@Test
	@Transactional
	@DisplayName("이미지가 없는 여러 프로젝트 게시글 생성")
	void createMultiProjectContent() throws IOException {
		Users user = createUser();
		List<NewContentDTO> contentDtoList = new ArrayList<>(); 
		for (int i = 0; i < 100; i++) {
			contentDtoList.add(createProjectContentDTO(user, "테스트 게시물 " + i));
		}
		
		List<DisplayedContentDTO> dbContentList = new ArrayList<>();
		for (int i = 0; i < contentDtoList.size(); i++) {
			dbContentList.add(contentService.createProjectContent(contentDtoList.get(i)));
		}
		
		for (int i = 0; i < contentDtoList.size(); i++) {
			checkEqual(contentDtoList.get(i), dbContentList.get(i));
		}
	}
	
	void checkEqual(NewContentDTO contentDto, DisplayedContentDTO createdContentDto) {
//		assertEquals(contentDto.getContent(), createdContentDto.getContent()); // 본문 내용은 HTML 문서상에서 올바르게 나오면 되기 때문에 약간 차이가 있을 수 있음
		assertEquals(contentDto.getTitle(), createdContentDto.getTitle());
		assertEquals(contentDto.getUsedLanguage(), createdContentDto.getUsedLanguage());
		assertEquals(contentDto.getUsedLanguage(), createdContentDto.getUsedLanguage());
		assertEquals(contentDto.getUserEmail(), createdContentDto.getWriter().getEmail());
	}
	
	@Test
	@Transactional
	@DisplayName("존재하지 않는 사용자의 게시글 생성 시도")
	void createContentByNonExistentUser() throws IOException {
		Users user = createNonExistentUser();
		NewContentDTO contentDto = createProjectContentDTO(user, "테스트 게시물");
		
		UsersException e = assertThrows(UsersException.class, () -> {contentService.createProjectContent(contentDto);});
		
		assertEquals(UserErrorDescription.NOT_FOUND_USER, e.getErrorDescription()); 
	}
	
	@Test
	@Transactional
	@DisplayName("게시글 조회")
	void getContentViewTest() throws IOException {
		DisplayedContentDTO content = createSingleProjectContent();
		
		DisplayedContentDTO viewContent = contentService.viewProjectContent(content.getContentId());
		
		assertEquals(content.getTitle(), viewContent.getTitle());
		assertEquals(content.getContent(), viewContent.getContent());
		assertEquals(content.getViewCount() + 1, viewContent.getViewCount());
	}
	
	@Test
	@Transactional
	@DisplayName("Content 수정")
	void createSingleContent() throws IOException {
		DisplayedContentDTO createdContent = createSingleProjectContent();
		String modifiedStr = "수정된 문자열";
		NewContentDTO modifiedContentDetails = NewContentDTO.builder()
				.contentId(createdContent.getContentId())
				.title(createdContent.getTitle()+modifiedStr)
				.content(modifiedStr + modifiedStr + modifiedStr)
				.usedLanguage("C++")
				.isRecruit(createdContent.isRecruit())
				.images(null) // 일단 null로 설정
				.build();
		
		DisplayedContentDTO modifiedContent = contentService.modifyProjectContent(modifiedContentDetails);
		
		assertEquals(createdContent.getContentId(), modifiedContent.getContentId());
		assertEquals(modifiedContentDetails.getTitle(), modifiedContent.getTitle());
		assertEquals(modifiedContentDetails.getContent(), modifiedContent.getContent());
		assertEquals(modifiedContentDetails.getUsedLanguage(), modifiedContent.getUsedLanguage());
	}
	
}
