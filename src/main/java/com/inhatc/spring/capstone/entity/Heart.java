package com.inhatc.spring.capstone.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.inhatc.spring.capstone.entity.project.ProjectContent;
import com.inhatc.spring.capstone.entity.user.Users;

@Entity
@Table(name = "heart")
/** 추천-좋아요-하트 테이블 엔티티 */
public class Heart {
	/*
	추천 번호
	사용자 번호 - FK 
	프로젝트 게시글 번호 - FK
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "project_content_id")
	private ProjectContent project;
	
	@ManyToOne(fetch =FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users likedUser;
}
