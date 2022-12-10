package com.inhatc.spring.capstone.file.repository;

import java.util.List;

import com.inhatc.spring.capstone.file.entity.SavedFile;

public interface SavedFileRepositoryCustom {
	List<SavedFile> getContentImgs(Long contentId);
}
