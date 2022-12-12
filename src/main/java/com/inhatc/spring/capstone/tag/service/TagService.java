package com.inhatc.spring.capstone.tag.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inhatc.spring.capstone.tag.constant.TagType;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.tag.entity.Tag;
import com.inhatc.spring.capstone.tag.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
	
	private final TagRepository tagRepository;
	
	/** 유사한 태그 검색 */
	public List<DisplayedTagDTO> searchSimilarTags(String tag, Pageable pageable) {
		return tagRepository.getSimilarTags(tag, pageable);
	}
	
	public DisplayedTagDTO saveCustomTag(DisplayedTagDTO tagDto) {
		Tag savedTag;
		if(!tagDto.getTagType().toUpperCase().equals(TagType.NEW.toString())) {
			
			if(tagDto.getTagId() == null) 
				throw new IllegalArgumentException();
			
			savedTag = tagRepository.findById(tagDto.getTagId()).orElseThrow(EntityNotFoundException::new);
		}
		else {
			savedTag = Tag.createCustomTag(tagDto.getTagName());
			savedTag = tagRepository.save(savedTag);
		}
		
		
		return DisplayedTagDTO.builder()
				.tagId(savedTag.getId())
				.tagName(savedTag.getName())
				.tagType(savedTag.getType().toString())
				.build();
	}
}
