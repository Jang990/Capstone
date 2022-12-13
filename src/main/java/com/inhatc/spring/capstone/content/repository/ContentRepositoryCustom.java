package com.inhatc.spring.capstone.content.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedSummaryContentDTO;

public interface ContentRepositoryCustom {
	DisplayedContentDTO getContentDetails(Long contentId);
	Page<DisplayedSummaryContentDTO> getSummaryContentPage(Pageable pageable);
}
