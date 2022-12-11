package com.inhatc.spring.capstone.tag.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.tag.entity.Tag;
import com.inhatc.spring.capstone.tag.repository.TagRepository;

@SpringBootTest
class TagServiceTest {

	@Autowired
	TagRepository tagRepository;
	
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

}
