package com.inhatc.spring.capstone.content.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
/** 사용자에게 보여질 댓글 정보 */
public class DisplayedCommentDTO {
	private Long commentId; // 댓글 ID
	private String writer; // 댓글 작성자
	private String modifyDate; // 댓글 작성 or 수정일
	private String comment; // 댓글 내용
}
