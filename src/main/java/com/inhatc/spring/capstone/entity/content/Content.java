package com.inhatc.spring.capstone.entity.content;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.inhatc.spring.capstone.entity.base.ContentBase;
import com.inhatc.spring.capstone.entity.file.SavedFile;

@Entity
@Table(name = "project_content")
/** 프로젝트 소개 게시글 테이블 엔티티 */
public class Content extends ContentBase {
	/*
	게시글번호- PK
	제목
	내용 
	글등록시간
	언어 (- 깃허브 API로 링크받아서 등록)
	사용자 번호 - FK (- 메일, 이름)
	
	이미지
	추천 수
	 */
	
	@Id
	@Column(name = "project_content_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "id")
	List<SavedFile> files = new ArrayList<>();
}