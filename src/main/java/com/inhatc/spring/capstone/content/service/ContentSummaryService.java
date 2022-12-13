package com.inhatc.spring.capstone.content.service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.inhatc.spring.capstone.content.dto.DisplayedSummaryContentDTO;
import com.inhatc.spring.capstone.content.repository.ContentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
/** 메인페이지와 같이 컨텐츠를 요약해서 조회하는 서비스 */
public class ContentSummaryService {
	private final ContentRepository contentRepository;
	
	/** 페이징해서 화면에 필요한 데이터를 보내줌 */
	public List<DisplayedSummaryContentDTO> getSummaryContents(Pageable pageable) {
		return null;
	}
}
