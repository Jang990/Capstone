package com.inhatc.spring.capstone.tag.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.tag.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
	
	private final TagRepository tagRepository;
	
	/** 유사한 태그 검색 */
	public List<DisplayedTagDTO> searchSimilarTags(String tag, Pageable pageable) {
		return tagRepository.getSimlarTags(tag, pageable);
	}
}
