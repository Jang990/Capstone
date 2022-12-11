package com.inhatc.spring.capstone.tag.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DisplayedTagDTO {
	private Long tagId; // DB에 있는 태그 아이디
	private String tagName; // 태그 이름  ex) Java Spring 등등
	private String tagType; // 태그 타입  ex) Custom인지 Tech인지

	@QueryProjection
	public DisplayedTagDTO(Long tagId, String tagName, String tagType) {
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagType = tagType;
	}
	
}
