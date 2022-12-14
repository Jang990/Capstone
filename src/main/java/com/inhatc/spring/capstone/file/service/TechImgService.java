package com.inhatc.spring.capstone.file.service;

import java.io.File;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.inhatc.spring.capstone.tag.constant.TagType;
import com.inhatc.spring.capstone.tag.entity.Tag;
import com.inhatc.spring.capstone.tag.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TechImgService {
	private final TagRepository tagRepository;

	public String getTechImgResourceSrc(Long tagId) {
		Tag tag = tagRepository.findById(tagId)
				.orElseThrow(EntityNotFoundException::new);
		
		if(tag.getType() != TagType.TECH) 
			throw new IllegalArgumentException();
		
		
		
		return null;
	}
}
