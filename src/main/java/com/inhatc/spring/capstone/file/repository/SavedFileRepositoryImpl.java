package com.inhatc.spring.capstone.file.repository;

import static com.inhatc.spring.capstone.file.entity.QSavedFile.savedFile;


import java.util.List;

import com.inhatc.spring.capstone.file.entity.SavedFile;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SavedFileRepositoryImpl implements SavedFileRepositoryCustom {
	private final JPAQueryFactory query;
	
	/** 컨텐츠에 해당하는 이미지를 다 가져옴 */
	public List<SavedFile> getContentImgs(Long contentId) {
		return query.selectFrom(savedFile)
				.where(savedFile.projectContent.id.eq(contentId))
				.fetch();
	}
	
	public SavedFile getImage(String savedPath) {
		return query.selectFrom(savedFile)
				.where(savedFile.savedPath.eq(savedPath))
				.fetchFirst();
	}
}
