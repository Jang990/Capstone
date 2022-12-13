package com.inhatc.spring.capstone.comment.entity;

import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.entity.base.CreatedAndUpdated;
import com.inhatc.spring.capstone.user.entity.Users;

@Entity
@Table(name = "comment")
/** 댓글 테이블 엔티티 */
public class Comment extends CreatedAndUpdated {
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
	private Long id; // 댓글 ID
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users writer; // 작성자 
	
	@ManyToOne
	@JoinColumn(name = "content_id")
	private Content content; // 댓글이 달린 컨텐츠
	
	private String comments; // 댓글 내용
}
