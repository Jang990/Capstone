package com.inhatc.spring.capstone.tag.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
/** API로 뷰쪽에 전달하기 위한 DTO */
public class TagListDTO {
	List<DisplayedTagDTO> tagList;
}
