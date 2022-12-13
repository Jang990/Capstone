package com.inhatc.spring.capstone.file.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.inhatc.spring.capstone.constant.FileType;
import com.inhatc.spring.capstone.content.entity.Content;
import com.inhatc.spring.capstone.file.dto.DisplayedImageDTO;

import lombok.Builder;
import lombok.Getter;

@Entity(name = "file")
@Getter
/** 사용자가 서버에 저장한 파일 엔티티 */
public class SavedFile {
	@Id
	@Column(name = "file_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long byteSize; // 사진 바이트 크기
	private int width; // 사진 너비
	private int height; // 사진 높이
	
	private String originalName; // 원본 이름
	private String savedPath; // 저장된 이름
	
	@Enumerated(EnumType.STRING)
	private FileType type; // JPG, PNG, GIF 등등 타입
	
	@ManyToOne
	@JoinColumn(name = "content_id")
	private Content projectContent;
	
	public SavedFile updateFile(String originalName, String savedName, String url, int width, int height) {
		/*
		 * 수정 사항
		 * DB에 URL이라는 컬럼 추가할지 말지 선택
		 */
		this.originalName = originalName;
		this.savedPath = savedName;
//		this.url = url;
		this.width = width;
		this.height = height;
		
		return this;
	}
	
	
	
	public static SavedFile createSavedImg(Content content,DisplayedImageDTO imgDto) {
		return SavedFile.builder()
				.byteSize(imgDto.getByteSize())
				.width(imgDto.getWidth())
				.height(imgDto.getHeight())
				.originalName(imgDto.getOriginalName())
				.savedPath(imgDto.getSavedPath())
				.projectContent(content)
				.build();
	}


	@Builder
	private SavedFile(Long byteSize, int width, int height, String originalName, String savedPath, Content projectContent) {
		this.byteSize = byteSize;
		this.width = width;
		this.height = height;
		this.originalName = originalName;
		this.savedPath = savedPath;
		this.projectContent = projectContent;
		
		this.type = FileType.PNG; // 꼭 필요한가?
	}
	
	/** 화면에 표시되는 width height 변경 */
	public SavedFile modifySavedImgSize(DisplayedImageDTO modifiedImg) {
		this.width = modifiedImg.getWidth();
		this.height = modifiedImg.getHeight();
		return this;
	}
}
