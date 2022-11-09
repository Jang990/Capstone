package com.inhatc.spring.capstone.entity.recuit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recruited_member")
/** 모집된 멤버 */
public class RecruitedMember {
	/*
	구인 인원 번호 - PK
	구인 공고 게시글 번호 - FK
	사용자 번호 -FK
	 */
	@Id
	@Column(name="recruited_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}
