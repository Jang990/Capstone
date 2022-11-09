package com.inhatc.spring.capstone.entity.content;

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

import com.inhatc.spring.capstone.entity.user.Users;
import com.inhatc.spring.capstone.util.BooleanToYNConverter;

@Entity
@Table(name = "content_vote")
/** 추천 투표 이력 테이블 엔티티 */
public class ContentVote {
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
	private Content project;
	
	@ManyToOne(fetch =FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users likedUser;
	
	@Convert(converter = BooleanToYNConverter.class)
	private boolean yn;
}
