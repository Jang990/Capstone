package com.inhatc.spring.capstone.content.dto;

import com.inhatc.spring.capstone.content.entity.Content;

public class DisplayedFileDTO {
	private Long id;
	
//	private int width; // 사진 너비
//	private int height; // 사진 높이
	
	private String originalFileName; // 원본 이름
	private String imgUrl; // 저장 위치
	
	private Content projectContent;


}
