package com.inhatc.spring.capstone.content.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.inhatc.spring.capstone.content.dto.QDisplayedContentDTO is a Querydsl Projection type for DisplayedContentDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QDisplayedContentDTO extends ConstructorExpression<DisplayedContentDTO> {

    private static final long serialVersionUID = 1817637077L;

    public QDisplayedContentDTO(com.querydsl.core.types.Expression<Long> contentId, com.querydsl.core.types.Expression<? extends com.inhatc.spring.capstone.user.dto.DisplayedUserDTO> writer, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> accessDate, com.querydsl.core.types.Expression<String> usedLanguage, com.querydsl.core.types.Expression<Boolean> isRecruit, com.querydsl.core.types.Expression<Integer> viewCount, com.querydsl.core.types.Expression<? extends java.util.List<DisplayedCommentDTO>> comments, com.querydsl.core.types.Expression<? extends java.util.List<DisplayedFileDTO>> files, com.querydsl.core.types.Expression<Integer> voteCount) {
        super(DisplayedContentDTO.class, new Class<?>[]{long.class, com.inhatc.spring.capstone.user.dto.DisplayedUserDTO.class, String.class, String.class, java.time.LocalDateTime.class, String.class, boolean.class, int.class, java.util.List.class, java.util.List.class, int.class}, contentId, writer, title, content, accessDate, usedLanguage, isRecruit, viewCount, comments, files, voteCount);
    }

}

