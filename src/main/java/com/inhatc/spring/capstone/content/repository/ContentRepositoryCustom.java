package com.inhatc.spring.capstone.content.repository;

import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;

public interface ContentRepositoryCustom {
	DisplayedContentDTO getContentDetails(Long contentId);
}
