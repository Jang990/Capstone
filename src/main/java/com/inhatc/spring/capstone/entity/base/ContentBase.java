package com.inhatc.spring.capstone.entity.base;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.inhatc.spring.capstone.entity.user.Users;

/** 게시글 부모 클래스 */
@MappedSuperclass
public class ContentBase {
	@ManyToOne
//	@Column(name = "writer_id")
	@JoinColumn(name = "user_id")
	private Users writer; // 게시물 작성자
	private String title; // 제목
	private String content; // 내용
	private String used_language; // 사용 언어 - 나중에 GitHub API를 사용하면서 타입을 바꿀 것
	private int view_count; // 조회수 - 쿠키로 조회수 중복을 제거할 것이다.
	
	private Timestamp date_created;  // 글 등록 시간
	private Timestamp last_updated; // 글 수정 시간
}
