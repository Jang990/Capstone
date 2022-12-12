package com.inhatc.spring.capstone.tag.service;

import static org.junit.jupiter.api.Assertions.*; 

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.spring.capstone.tag.constant.TagType;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.tag.entity.Tag;
import com.inhatc.spring.capstone.tag.repository.TagRepository;

@SpringBootTest
class TagServiceTest {

	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	TagService tagService;
	
	@Test
	@DisplayName("단순 QueryDSL 확인용 테스트")
	@Transactional
	void queryTest() {
		for (int i = 0; i < 30; i++) {
			if(i%2 == 0) {
				tagRepository.save(Tag.createCustomTag(DisplayedTagDTO.builder().tagName("테스트관련태그 " + i).tagType(TagType.NEW.toString()).build()));
			}
			else {
				tagRepository.save(Tag.createCustomTag(DisplayedTagDTO.builder().tagName("테스트관련Tag1 " + i).tagType(TagType.NEW.toString()).build()));
			}
		}
		
		List<DisplayedTagDTO> simlarTags = tagRepository.getSimilarTags("테스트관련Ta", PageRequest.of(0, 10));
	}
	
	@Test
	@DisplayName("커스텀 태그저장 기능 테스트")
	@Transactional
	void saveCustomTagTest() {
		List<DisplayedTagDTO> newTagList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			newTagList.add(createNewTag("Test 생성 태그" + i));
		}
		
		List<DisplayedTagDTO> savedTagList = new ArrayList<>();
		for (DisplayedTagDTO displayedTagDTO : newTagList) {
			savedTagList.add(DisplayedTagDTO.of(tagService.createCustomTag(displayedTagDTO)));
		}
		
		for (int i = 0; i < savedTagList.size(); i++) {
			DisplayedTagDTO newTag = newTagList.get(i);
			DisplayedTagDTO savedTag = savedTagList.get(i);
			
			assertEquals(newTag.getTagName(), savedTag.getTagName());
			assertEquals(newTag.getTagType(), TagType.NEW.toString());
			assertEquals(savedTag.getTagType(), TagType.CUSTOM.toString());
			assertNotEquals(savedTag.getTagId(), null);
		}
	}
	
	@Test
	@DisplayName("이미 저장되어 있는 태그 포함 save 테스트")
	@Transactional
	void saveCustomTagTest2() {
		List<DisplayedTagDTO> newTagList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			if(i%2 == 0)
				newTagList.add(createNewTag("Test 생성 태그" + i));
			else
				newTagList.add(createSavedCustomTag("저장되어 있는 태그"+i));
		}
		
		List<DisplayedTagDTO> savedTagList = new ArrayList<>();
		for (DisplayedTagDTO displayedTagDTO : newTagList) {
			DisplayedTagDTO savedTag;
			if(displayedTagDTO.getTagType().equals(TagType.NEW.toString())) {
				savedTag = DisplayedTagDTO.of(tagService.createCustomTag(displayedTagDTO));
			}
			else {
				savedTag = DisplayedTagDTO.of(tagService.getExistTag(displayedTagDTO)); 
			}
			savedTagList.add(savedTag);
		}
		
		for (int i = 0; i < savedTagList.size(); i++) {
			DisplayedTagDTO newTag = newTagList.get(i);
			DisplayedTagDTO savedTag = savedTagList.get(i);
			System.out.println(savedTag);
			
			assertEquals(newTag.getTagName(), savedTag.getTagName());
			assertEquals(savedTag.getTagType(), TagType.CUSTOM.toString());
			assertNotEquals(savedTag.getTagId(), null);
		}
	}
	
	@Test
	@DisplayName("여러 에러 태그 저장 테스트")
	@Transactional
	void saveCustomTagTest3() {
		List<DisplayedTagDTO> newTagList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			if(i%3 == 0)
				newTagList.add(createNewTag("Test 생성 태그" + i));
			else if(i%3 == 1)
				newTagList.add(createSavedCustomTag("저장되어 있는 태그"+i));
			else
				newTagList.add(createErrorTag("에러태그"+i)); 
		}
		
		for (int i = 0; i < newTagList.size(); i++) {
			DisplayedTagDTO displayedTagDTO = newTagList.get(i);
			if(i%3 == 0)
				tagService.createCustomTag(displayedTagDTO);
			else if(i%3 == 1)
				tagService.getExistTag(displayedTagDTO);
			else
				assertThrows(IllegalArgumentException.class, () -> {tagService.createCustomTag(displayedTagDTO);});
		}
	}
	
	@Test
	@DisplayName("여러 에러 태그 가져오기 테스트")
	@Transactional
	void getErrorTagTest() {
		DisplayedTagDTO errorTag1 = createMismatchErrorTag("에러 태그");
		IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> {tagService.getExistTag(errorTag1);});
		
		DisplayedTagDTO errorTag2 = createNotFoundErrorTag("에러 태그2");
		EntityNotFoundException e2 = assertThrows(EntityNotFoundException.class, () -> {tagService.getExistTag(errorTag2);});
	}
	
	// save - 프론트엔드에서 새로 만들어진 태그
	DisplayedTagDTO createNewTag(String tagName) {
		return DisplayedTagDTO.builder()
				.tagName(tagName)
				.tagType(TagType.NEW.toString())
				.build();
	}
	
	// save- 저장되어 있는 Custom 태그
	DisplayedTagDTO createSavedCustomTag(String tagName) {
		DisplayedTagDTO displayedTagDTO = DisplayedTagDTO.builder()
			.tagName(tagName)
			.tagType(TagType.NEW.toString())
			.build();
		
		return DisplayedTagDTO.of(tagService.createCustomTag(displayedTagDTO));
	}
	
	// save - 에러 발생 태그(id가 없지만 New가 아님)
	DisplayedTagDTO createErrorTag(String tagName) {
		return DisplayedTagDTO.builder()
				.tagName(tagName)
				.tagType(TagType.TECH.toString())
				.build();
	}
	
	// get - 에러 발생 태그(id 존재 하지 않음)
	DisplayedTagDTO createNotFoundErrorTag(String tagName) {
		return DisplayedTagDTO.builder()
				.tagId(Long.MIN_VALUE)
				.tagName(tagName)
				.tagType(TagType.CUSTOM.toString())
				.build(); 
	}
	
	// get - 에러 발생 태그(기존 저장된 id와 tagName이 매칭되지 않음)
	DisplayedTagDTO createMismatchErrorTag(String tagName) {
		DisplayedTagDTO displayedTagDTO = DisplayedTagDTO.builder()
				.tagName(tagName)
				.tagType(TagType.NEW.toString())
				.build();
			
		displayedTagDTO = DisplayedTagDTO.of(tagService.createCustomTag(displayedTagDTO));
		
		return DisplayedTagDTO.builder()
				.tagId(displayedTagDTO.getTagId())
				.tagName(displayedTagDTO.getTagName()+"오류 문자가 포함됨")
				.tagType(TagType.TECH.toString())
				.build(); 
	}		
	
	
}
