package com.inhatc.spring.capstone.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
/** 댓글 테이블 엔티티 */
public class Comment {
	/*
	댓글 번호
	프로젝트 게시글 번호 - FK
	사용자 번호 - FK
	댓글 내용
	댓글 작성 시간
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}
