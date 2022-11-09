package com.inhatc.spring.capstone.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.inhatc.spring.capstone.entity.content.Content;
import com.inhatc.spring.capstone.entity.user.Users;

@Entity
@Table(name = "comment")
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
	@Column(name = "comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users writer;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Content project;
	
	@CreationTimestamp
	private Timestamp createdTime;
	
	@UpdateTimestamp
	private Timestamp updatedTime;
	
	private String content;
}
