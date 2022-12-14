package com.inhatc.spring.capstone.entity.board;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data

public class board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int people;//백엔드 프론트엔드 언어별 (인원수)
	
	private String title;//프로젝트 제목
	
	private String content;//텍스트에디터 값
	
	private String usedLanguage;//언어
	
	private String Selectbox;//백엔드 프론트엔드

}
