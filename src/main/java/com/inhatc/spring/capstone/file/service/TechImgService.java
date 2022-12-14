package com.inhatc.spring.capstone.file.service;

import java.io.File;
import java.io.IOException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.inhatc.spring.capstone.tag.constant.TagType;
import com.inhatc.spring.capstone.tag.entity.Tag;
import com.inhatc.spring.capstone.tag.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TechImgService {
	private final TagRepository tagRepository;
	
	@Value(value = "${techIconLocation}")
	private String techLocation;

	public String getTechImgResourceSrc(Long tagId) throws IOException {
		Tag tag = tagRepository.findById(tagId)
				.orElseThrow(EntityNotFoundException::new);
		
		if(tag.getType() != TagType.TECH) 
			throw new IllegalArgumentException();
		
		ClassPathResource cpr = new ClassPathResource(techLocation+"/"+tag.getName().toLowerCase());
		File file = cpr.getFile();
		File[] listFiles = file.listFiles();
		
		for (File file2 : listFiles) {
			String fileName = file2.getName();
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
			if(ext.equals("svg")) {
				return "./devicon/icons/"+tag.getName()+"/"+fileName;
			}
		}
		
		throw new IllegalArgumentException();
	}
}
