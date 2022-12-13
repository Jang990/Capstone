package com.inhatc.spring.capstone.content.repository;

import static com.inhatc.spring.capstone.content.entity.QContent.content1;
import static com.inhatc.spring.capstone.file.entity.QSavedFile.savedFile;
import static com.inhatc.spring.capstone.tag.entity.QContentTag.contentTag;
import static com.inhatc.spring.capstone.tag.entity.QTag.tag;
import static com.inhatc.spring.capstone.user.entity.QUsers.users;
import static com.querydsl.core.group.GroupBy.list;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.inhatc.spring.capstone.content.dto.DisplayedCommentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedFileDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedSummaryContentDTO;
import com.inhatc.spring.capstone.content.dto.QDisplayedContentDTO;
import com.inhatc.spring.capstone.content.dto.QDisplayedSummaryContentDTO;
import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.tag.dto.QDisplayedTagDTO;
import com.inhatc.spring.capstone.user.dto.DisplayedUserDTO;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContentRepositoryImpl implements ContentRepositoryCustom {
	private final JPAQueryFactory query;
	
	public DisplayedContentDTO getContentDetails(Long contentId) {
		DisplayedContentDTO contentDetail = query
				.from(content1)
				.join(content1.writer, users)
				.innerJoin(contentTag).on(contentTag.content.eq(content1))
				.innerJoin(tag).on(tag.eq(contentTag.tag))
				.where(content1.id.eq(contentId))
				.transform(
						GroupBy.groupBy(content1.id).list(
								new QDisplayedContentDTO(
										content1.id.as("contentId"), 
										Projections.fields(DisplayedUserDTO.class, users.email, users.name), 
										content1.title,
										content1.content, 
										content1.last_updated.as("accessDate"), 
										content1.usedLanguage, 
										content1.isRecruit, 
										content1.viewCount, 
										list(
												// 일단 값은 안채워넣음
												Projections.fields(DisplayedCommentDTO.class)
										), 
										list(
												// 일단 값은 안채워 넣음
												Projections.fields(DisplayedFileDTO.class)
										),
										content1.heartCount,
										list(
//												Projections.fields(DisplayedTagDTO.class)
												new QDisplayedTagDTO(tag.id, tag.name, tag.type.stringValue())
										)
								)
						)
				).get(0);
		
		return contentDetail;
	}
	
	public Page<DisplayedSummaryContentDTO> getSummaryContentPage(Pageable pageable) {
		List<DisplayedSummaryContentDTO> summaryContentList = query
				.selectFrom(content1)
				.join(content1.writer, users)
				.leftJoin(contentTag).on(contentTag.content.eq(content1))
				.leftJoin(tag).on(tag.eq(contentTag.tag))
				.leftJoin(savedFile).on(savedFile.projectContent.eq(content1))
//				.where(null) // 이 부분은 완성하고 테스트한다음 검색 기능 하면서 수정
				.orderBy(content1.heartCount.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.transform(
						GroupBy.groupBy(content1.id).list(
								new QDisplayedSummaryContentDTO(
										content1.id, 
										content1.title, 
										savedFile.savedPath, 
										list(
												new QDisplayedTagDTO(tag.id, tag.name, tag.type.stringValue())
										), 
										users.name, 
										content1.viewCount, 
										content1.heartCount
									)
						)
				);
		
		for (DisplayedSummaryContentDTO summaryContent : summaryContentList) {
			if(summaryContent.getTags().get(0).getTagId() == null)
				summaryContent.setTags(new ArrayList<>());
		}
		
		
		long total = query.select(content1.count()).from(content1).fetchOne();
		
		return new PageImpl<DisplayedSummaryContentDTO>(summaryContentList, pageable, total);
	}
}