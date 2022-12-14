package com.inhatc.spring.capstone.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
/** 댓글을 등록할 때 사용될 DTO */
public class NewCommentDTO {
	private String commentBody; // 댓글 내용
}
