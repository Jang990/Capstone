package com.inhatc.spring.capstone.tag.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
				tagRepository.save(Tag.createCustomTag("테스트관련태그 " + i));
			}
			else {
				tagRepository.save(Tag.createCustomTag("테스트관련Tag1 " + i));
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
			savedTagList.add(tagService.saveCustomTag(displayedTagDTO));
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
	
	// 프론트엔드에서 새로 만들어진 태그
	DisplayedTagDTO createNewTag(String tagName) {
		return DisplayedTagDTO.builder()
				.tagName(tagName)
				.tagType(TagType.NEW.toString())
				.build();
	}
	
}
