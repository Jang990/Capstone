package com.inhatc.spring.capstone.entity.recuit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.inhatc.spring.capstone.entity.base.ContentBase;

@Entity
@Table(name = "member_content")
/** 멤버 모집 테이블 엔티티*/
public class MemberContent extends ContentBase {
	/*
	구인공고 게시글 번호 - PK
	제목
	내용
	글등록시간
	사용자 번호 - FK (- 메일, 이름)
	사용 언어
	
	모집기간
	모집현황
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}
