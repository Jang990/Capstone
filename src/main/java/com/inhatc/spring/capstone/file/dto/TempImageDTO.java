package com.inhatc.spring.capstone.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DB에는 없는 임시저장한 파일 DTO
 */
@Getter
@NoArgsConstructor
public class TempImageDTO {
	private Long byteSize; // 사진 바이트 크기
	
	/*
	 * 너비와 높이는 계속 변경된다. - 저장시점의 너비와 높이만 알면된다.
	 * private int width; // 사진 너비
	 * private int height; // 사진 높이
	 * 
	 * DB에 저장할 때만 필요
	 * private FileType type; // JPG, PNG, GIF 등등 타입
	*/
	private String originalName; // 원본 이름
	private String savedPath; // 저장된 파일 Path

	@Builder
	public TempImageDTO(Long byteSize, String originalName, String savedPath) {
		this.byteSize = byteSize;
		this.originalName = originalName;
		this.savedPath = savedPath;
	}
	
}
