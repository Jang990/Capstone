package com.inhatc.spring.capstone.tag.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.tag.dto.TagListDTO;
import com.inhatc.spring.capstone.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {
	
	private final TagService tagService;
	
	/* 사용자 정의 태그 생성은 Content를 작성할 때 일단 만들어두면 Content를 저장할 때 생성하면 된다. */
	
	@GetMapping("/similar")
	/** 현재 태그와 유사한 태그를 10개 검색해서 보내준다. */
	public ResponseEntity<TagListDTO> craeteCustomTag(String tag,
			@PageableDefault(page = 0, size = 3) Pageable pageable) {
		List<DisplayedTagDTO> similarTagList = tagService.searchSimilarTags(tag, pageable);
		return new ResponseEntity<>(new TagListDTO(similarTagList), HttpStatus.OK);
	}
}
