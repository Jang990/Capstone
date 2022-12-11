package com.inhatc.spring.capstone.tag.repository;

import static com.inhatc.spring.capstone.tag.entity.QTag.tag;
import static com.querydsl.core.group.GroupBy.groupBy;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;
import com.inhatc.spring.capstone.tag.dto.QDisplayedTagDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepositoryCustom {
	private final JPAQueryFactory query;
	
	public List<DisplayedTagDTO> getSimlarTags(String tagKeyword, Pageable pageable) {
		return  query.selectFrom(tag)
				.where(tag.name.contains(tagKeyword))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.orderBy(tag.taggedCount.desc())
				.transform(
						groupBy(tag.id).list(
								new QDisplayedTagDTO(tag.id, tag.name, tag.type.stringValue())
						)
				);
	}
}
