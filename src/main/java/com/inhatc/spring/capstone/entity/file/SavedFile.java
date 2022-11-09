package com.inhatc.spring.capstone.entity.file;

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
import com.inhatc.spring.capstone.entity.project.ProjectContent;

@Entity(name = "file")
public class SavedFile {
	@Id
	@Column(name = "file_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int byteSize; // 사진 바이트 크기
	private int width; // 사진 너비
	private int height; // 사진 높이
	
	private String originalName; // 원본 이름
	private String name; // 저장된 이름
	
	@Enumerated(EnumType.STRING)
	private FileType type; // JPG, PNG, GIF 등등 타입
	
	@ManyToOne
	@JoinColumn(name = "content")
	private ProjectContent projectContent;
}
