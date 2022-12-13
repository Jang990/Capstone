package com.inhatc.spring.capstone.content.dto;

import java.util.List;

import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
/** 메인페이지 같은 곳에 표시되는 프로젝트 리스트에 들어갈 데이터 */
public class DisplayedSummaryContentDTO {
	/*
	 * 제목
	 * 대표 이미지
	 * 관련 태그들 (최대 3개)
	 * 사용자 이름
	 * 조회 수
	 * 하트 수
	 */
	
	private String title; // 제목
	private String imgSavedPath; // 대표 이미지 리소스 접근 위치
	private List<DisplayedTagDTO> tags; // 지정 태그
	private String username; // 사용자 이름
	private int hits; // 조회 수
	private int heart; // 하트 수
}
