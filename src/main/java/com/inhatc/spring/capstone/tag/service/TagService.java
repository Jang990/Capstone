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
	
	/** 태그 저장 */
	public DisplayedTagDTO saveCustomTag(DisplayedTagDTO tagDto) {
		if(!tagDto.getTagType().toUpperCase().equals(TagType.NEW.toString())) {
			return getTag(tagDto);
		}

		Tag savedTag = Tag.createCustomTag(tagDto.getTagName());
		savedTag = tagRepository.save(savedTag);
		
		return DisplayedTagDTO.builder()
				.tagId(savedTag.getId())
				.tagName(savedTag.getName())
				.tagType(savedTag.getType().toString())
				.build();
	}
	
	/** 태그 가져오기 */
	public DisplayedTagDTO getTag(DisplayedTagDTO tagDto) {
		if(tagDto.getTagId() == null) 
			throw new IllegalArgumentException();
		
		Tag tag = tagRepository.findById(tagDto.getTagId()).orElseThrow(EntityNotFoundException::new);
		
		if(!tag.getName().equals(tagDto.getTagName())
				|| !tag.getType().toString().equals(tagDto.getTagName().toUpperCase()))
			throw new IllegalArgumentException();
		
		return DisplayedTagDTO.builder()
				.tagId(tag.getId())
				.tagName(tag.getName())
				.tagType(tag.getType().toString())
				.build();
	}
}
