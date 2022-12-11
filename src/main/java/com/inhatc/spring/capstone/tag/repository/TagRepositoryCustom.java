package com.inhatc.spring.capstone.tag.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;

public interface TagRepositoryCustom {
	List<DisplayedTagDTO> getSimlarTags(String tagKeyword, Pageable pageable);
}
