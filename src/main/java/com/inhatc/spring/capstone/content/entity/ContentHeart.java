package com.inhatc.spring.capstone.content.entity;

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

import com.inhatc.spring.capstone.user.entity.Users;
import com.inhatc.spring.capstone.util.BooleanToYNConverter;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "content_heart", uniqueConstraints = {
		@UniqueConstraint(
		columnNames = {"content_id", "user_id"}
)
})
/** 추천 투표 이력 테이블 엔티티 */
public class ContentHeart {
	/*
	추천 번호
	사용자 번호 - FK 
	프로젝트 게시글 번호 - FK
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "content_id")
	private Content content;
	
	@ManyToOne(fetch =FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users likedUser;
	
	@Builder
	public ContentHeart(Content content, Users likedUser) {
		this.content = content;
		this.likedUser = likedUser;
	}
	
	
}
