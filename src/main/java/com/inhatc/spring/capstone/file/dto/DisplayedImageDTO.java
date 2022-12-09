package com.inhatc.spring.capstone.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class DisplayedImageDTO {
	private Long byteSize; // 사진 바이트 크기
	private int width; // 사진 너비
	private int height; // 사진 높이
	private String originalName; // 원본 이름
	private String savedPath; // 저장된 파일 Path
	
	@Builder
	public DisplayedImageDTO(Long byteSize, int width, int height, String originalName, String savedPath) {
		this.byteSize = byteSize;
		this.width = width;
		this.height = height;
		this.originalName = originalName;
		this.savedPath = savedPath;
	}
}
