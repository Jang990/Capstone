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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedSummaryContentDTO;
import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.content.repository.ContentRepository;
import com.inhatc.spring.capstone.content.service.ContentService;
import com.inhatc.spring.capstone.file.service.TemporaryImageService;
import com.inhatc.spring.capstone.tag.constant.TagType;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.tag.entity.ContentTag;
import com.inhatc.spring.capstone.tag.entity.Tag;
import com.inhatc.spring.capstone.tag.repository.ContentTagRepository;
import com.inhatc.spring.capstone.tag.repository.TagRepository;
import com.inhatc.spring.capstone.tag.service.TagService;
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
	private TagRepository tagRepository;
	@Autowired
	private ContentRepository contentRepository;
	@Autowired
	private ContentTagRepository contentTagRepository;
	
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
				.userEmail(user.getEmail())
				.title(title)
				.content(str)
				.usedLanguage("JAVA")
				.isRecruit(false)
				.build();
	}
	
	// 사용자가 만든 태그가 리스트 생성 - 단일 프로젝트 DTO 전용
	List<DisplayedTagDTO> createTags() {
		List<DisplayedTagDTO> tags = new ArrayList<>();
		tags.add(
				DisplayedTagDTO.builder()
					.tagName("테스트 생성 태그 1")
					.tagType(TagType.NEW.toString())
					.build()
				);
		
		
		Tag savedTag = Tag.builder().name("테스트 이미 저장되어 있는 태그 2").type(TagType.CUSTOM).build();
		tagRepository.save(savedTag);
		tags.add(
				DisplayedTagDTO.builder()
					.tagId(savedTag.getId())
					.tagName(savedTag.getName())
					.tagType(savedTag.getType().toString())
					.build()
			);
		
		return tags;
	}
	
	
	// 단일 프로젝트 생성
	DisplayedContentDTO createSingleProjectContent() throws IOException {
		Users user = createUser();
		NewContentDTO contentDto = createProjectContentDTO(user, "테스트 게시물");
		contentDto.setTags(createTags());
		
		DisplayedContentDTO createdContentDto = contentService.createProjectContent(contentDto);
		
		checkEqual(contentDto, contentRepository.findById(createdContentDto.getContentId()).get());
		return createdContentDto;
	}
	
	@Test
	@Transactional
	@DisplayName("이미지가 없는 단일 프로젝트 게시글 생성")
	void createSingleProjectContentTest() throws IOException {
		Users user = createUser();
		NewContentDTO contentDto = createProjectContentDTO(user, "테스트 게시물");
		contentDto.setTags(createTags());
		
		// 이미지가 있는경우 이렇게 테스트하면 되지만 나중에 테스트시에 해당 파일이 없으면 테스트 실패 - 따로 단위테스트를 해야할 것  같다.
//		NewContentDTO contentDto = createContentWithImage(user, "테스트 게시물");
		
		DisplayedContentDTO createdContentDto = contentService.createProjectContent(contentDto);
		
		checkEqual(contentDto, contentRepository.findById(createdContentDto.getContentId()).get());
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
			checkEqual(contentDtoList.get(i), contentRepository.findById(dbContentList.get(i).getContentId()).get());
		}
	}
	
	void checkEqual(NewContentDTO contentDto, Content createdContent) {
//		assertEquals(contentDto.getContent(), createdContentDto.getContent()); // 본문 내용은 HTML 문서상에서 올바르게 나오면 되기 때문에 약간 차이가 있을 수 있음
		assertEquals(contentDto.getTitle(), createdContent.getTitle());
		assertEquals(contentDto.getUsedLanguage(), createdContent.getUsedLanguage());
		assertEquals(contentDto.getUsedLanguage(), createdContent.getUsedLanguage());
		assertEquals(contentDto.getUserEmail(), createdContent.getWriter().getEmail());
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
		
		List<DisplayedTagDTO> beforeTags = createdContent.getTags();
		
		String modifiedStr = "수정된 문자열";
		
		// 수정된 태그
		List<DisplayedTagDTO> modifiedTags = new ArrayList<>(); // 이전 태그를 전부 없애버림
		DisplayedTagDTO addTag = DisplayedTagDTO.builder()
				.tagType(TagType.NEW.toString())
				.tagName("수정되면서 새로 만들어진 태그")
				.build(); // 추가된 태그
		modifiedTags.add(addTag);
		
		NewContentDTO modifiedContentDetails = NewContentDTO.builder()
				.contentId(createdContent.getContentId())
				.title(createdContent.getTitle()+modifiedStr)
				.content(modifiedStr + modifiedStr + modifiedStr)
				.usedLanguage("C++")
				.isRecruit(createdContent.isRecruit())
				.tags(modifiedTags)
				.images(null) // 일단 null로 설정
				.build();
		
		DisplayedContentDTO modifiedContentDto = contentService.modifyProjectContent(modifiedContentDetails);
		Content modifiedContent = contentRepository.findById(modifiedContentDto.getContentId()).get();
		
		System.out.println("최종결과");
		modifiedContent.getTags().forEach(System.out::println);
		
		System.out.println("DB내용");
		tagRepository.findAll().forEach(System.out::println);
		
		assertEquals(createdContent.getContentId(), modifiedContent.getId());
		assertEquals(modifiedContentDetails.getTitle(), modifiedContent.getTitle());
		assertEquals(modifiedContentDetails.getContent(), modifiedContent.getContent());
		assertEquals(modifiedContentDetails.getUsedLanguage(), modifiedContent.getUsedLanguage());
		
		// 삭제된 태그들은 taggedCount가 0이 된다.
		assertEquals(tagRepository.findById(beforeTags.get(0).getTagId()).get().getTaggedCount(), 0);
		assertEquals(tagRepository.findById(beforeTags.get(1).getTagId()).get().getTaggedCount(), 0);
		// 추가된 태그는 taggedCount가 1이 된다.
		assertEquals(modifiedContent.getTags().stream().findFirst().get().getTag().getTaggedCount(), 1);
	}
	
	@Test
	@Transactional
	@DisplayName("querydsl테스트")
	void aaa() throws IOException {
//		createMultiProjectContent();
		
		List<Sort.Order> orders = new ArrayList<Sort.Order>();
//		orders.add(Order.desc("createdDate"));
		orders.add(Order.desc("heart"));
		Pageable pageable = PageRequest.of(0, 1, Sort.by(orders));
		
		
		List<String> searchList = new ArrayList<>();
//		searchList.add("spring");
//		searchList.add("csharp");
		Page<DisplayedSummaryContentDTO> page = contentRepository.getSummaryContentPage(pageable, searchList, null);
		System.out.println("로그찍어보기===========");
		page.getContent().forEach(System.out::println);
	}
	
	
}
