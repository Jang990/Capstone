package com.inhatc.spring.capstone.test.dto;

import javax.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class mainDto {
	
	@NotBlank(message = "검색어를 입력하여 주십시오")
	private String Searchtxt;  //검색텍스트 프로젝트 이름
	
	@NotBlank(message = "태그를 입력하여 주십시오")
	private String SeachTag; //검색텍스트 태그
	
	@NotBlank(message = "이름을 입력하여 주십시오")
	private String SearchName; //검색텍스트 이름
	
	private String RankingTag[];//태그랭킹
	
	
	
}
