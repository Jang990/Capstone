package com.inhatc.spring.capstone.entity.recuit;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.tag.entity.PositionTag;

@Entity
@Table(name = "recruited_member")
/** 모집 멤버 정보 */
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
	
	@ManyToOne
	@JoinColumn(name = "content_id")
	private Content content; // 게시글
	
	@OneToMany(mappedBy = "id")
	private List<PositionTag> tags; // 사용한 태그
	
	private int rec_count; // 구인 인원 수
}
