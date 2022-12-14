package com.inhatc.spring.capstone.content.dto;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.user.entity.Users;
import com.inhatc.spring.capstone.util.BooleanToYNConverter;
import com.querydsl.core.annotations.QueryProjection;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContentHeartDto {
	/*
	추천 번호
	사용자 번호 - FK 
	프로젝트 게시글 번호 - FK
	 */
	
	private Long id;
	private Content content;
	private Users likedUser;
		
}
