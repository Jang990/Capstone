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
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

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
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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
	
	public Page<DisplayedSummaryContentDTO> getSummaryContentPage(Pageable pageable, List<String> search, String userEmail) {
		List<DisplayedSummaryContentDTO> summaryContentList = query
				.selectFrom(content1)
				.join(content1.writer, users)
				.leftJoin(contentTag).on(contentTag.content.eq(content1))
				.leftJoin(tag).on(tag.eq(contentTag.tag))
				.leftJoin(savedFile).on(savedFile.projectContent.eq(content1))
				.where(
						searchTitleOrTag(search),
						searchUserContent(userEmail)
				)
				.orderBy(contentSort(pageable))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.groupBy(content1.id)
				.transform(
						GroupBy.groupBy(content1.id).list(
								new QDisplayedSummaryContentDTO(
										content1.id, 
										content1.title, 
										savedFile.savedPath, 
										list(
//												Projections.fields(DisplayedTagDTO.class)
												new QDisplayedTagDTO(tag.id, tag.name, tag.type.stringValue())
										), 
										users.name, 
										users.email,
										content1.viewCount, 
										content1.heartCount,
										content1.date_created
									)
						)
				);
		
		/*
		 * 콘텐츠 내용들~~~			태그 내용
		 * 1번 콘텐츠 ~~			1번태그
		 * 1번 콘텐츠 ~~			2번태그
		 * 
		 * 페이징을 할 때도 이런식으로 태그만 다른 중복 데이터가 이뤄져서 어떻게 변환할지 아직은 잘 모르겠음.
		 * 나중에 꼭 알아볼 것
		 */
		//임시방편 - 태그 쿼리 또 보내기
		for (DisplayedSummaryContentDTO content : summaryContentList) {
			if(content.getTags().get(0).getTagId() == null) {
				content.setTags(new ArrayList<>());
				continue;
			}
			
			List<DisplayedTagDTO> tags = query.selectFrom(contentTag)
					.join(contentTag.tag, tag)
					.where(contentTag.content.id.eq(content.getContentId()))
					.transform(
							GroupBy.groupBy(contentTag.id).list(
									new QDisplayedTagDTO(tag.id, tag.name, tag.type.stringValue())
							)
						);
			
			content.setTags(tags);
		}
		
		long total = query.select(content1.count()).from(content1).fetchOne();
		
		return new PageImpl<DisplayedSummaryContentDTO>(summaryContentList, pageable, total);
	}
	
	// 정렬 조건
	private OrderSpecifier<?> contentSort(Pageable pageable) {
		if(pageable.getSort().isEmpty()) {
			return null;
		}
		
//		content1.heartCount.desc()
		Sort sort = pageable.getSort();
		for (Sort.Order order : sort) {
			Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
			
			switch (order.getProperty()) {
				case "createdDate":
					return new OrderSpecifier(direction, content1.date_created);// 최신순
				case "heart":
					return new OrderSpecifier(direction, content1.heartCount); // 하트순
			}
		}
		
		return null;
	}

	private BooleanBuilder searchTitleOrTag(List<String> keywords) {
		BooleanBuilder builder = new BooleanBuilder();
		
		if(keywords.size() < 1) {
			return null;
		}
		
		for (String keyword : keywords) {
			if(StringUtils.hasText(keyword)) {
				builder.or(tag.name.containsIgnoreCase(keyword));
				builder.or(content1.title.containsIgnoreCase(keyword));
			}
		}
		return builder;
	}
	private BooleanBuilder searchUserContent(String userEmail) {
		if(!StringUtils.hasText(userEmail)) {
			return null;
		}
		
		BooleanBuilder builder = new BooleanBuilder();
		builder.or(users.email.eq(userEmail));
		return builder;
	}
}
