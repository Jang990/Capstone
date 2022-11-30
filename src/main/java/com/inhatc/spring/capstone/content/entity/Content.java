package com.inhatc.spring.capstone.content.entity;

import java.util.ArrayList; 
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.inhatc.spring.capstone.content.dto.NewContentDTO;
import com.inhatc.spring.capstone.content.dto.DisplayedContentDTO;
import com.inhatc.spring.capstone.entity.base.CreatedAndUpdated;
import com.inhatc.spring.capstone.file.entity.SavedFile;
import com.inhatc.spring.capstone.user.entity.Users;
import com.inhatc.spring.capstone.util.BooleanToYNConverter;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "content")
/** 작성글 정보 테이블 엔티티 */
public class Content extends CreatedAndUpdated{
	/*
	게시글번호- PK
	제목
	내용 
	글등록시간
	언어 (- 깃허브 API로 링크받아서 등록)
	사용자 번호 - FK (- 메일, 이름)
	
	이미지
	추천 수
	 */
	
	@Id
	@Column(name = "content_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
//	@Column(name = "writer_id")
	@JoinColumn(name = "user_id")
	private Users writer; // 게시물 작성자
	private String title; // 제목
	
	@Column(columnDefinition = "TEXT", nullable = false) // text는 길이를 설정하지 않음. 속성의 최대 길이를 모르면 text를 사용 적합
	private String content; // 내용
	
	private String usedLanguage; // 사용 언어 - 나중에 GitHub API를 사용하면서 타입을 바꿀 것
	private int viewCount; // 조회수 - 쿠키로 조회수 중복을 제거할 것이다.
	private int voteCount; // 찬반 카운트
	
	@Convert(converter = BooleanToYNConverter.class)
	@Column(length = 1)
	private boolean isRecruit;
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
	List<SavedFile> files = new ArrayList<>();
	
	public static Content createContent(Users writer, NewContentDTO contentDto) {
		return Content.builder()
				.writer(writer)
				.title(contentDto.getTitle())
				.content(contentDto.getContent())
				.usedLanguage(contentDto.getUsedLanguage())
				.viewCount(0)
				.voteCount(0)
				.isRecruit(contentDto.isRecruit())
				// DTO에서 엔티티 제거 후 MultipartFile 형식으로 바꾸고 여기서 SavedFile 엔티티 생성 예정
				.files(null) // 일단 null 넣어두고 추후 file 관련 추가하면서 DisplayedFiles에 of 추가 
				.build();
	}
	
	public Content modifyContent(NewContentDTO contentDto) {
		this.title = contentDto.getTitle();
		this.content = contentDto.getContent();
		this.usedLanguage = contentDto.getUsedLanguage();
		this.isRecruit = contentDto.isRecruit();
		
		return this;
	}
	
	
	@Builder
	public Content(Users writer, String title, String content, String usedLanguage, int viewCount, int voteCount,
			boolean isRecruit, List<SavedFile> files) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.usedLanguage = usedLanguage;
		this.viewCount = viewCount;
		this.voteCount = voteCount;
		this.isRecruit = isRecruit;
		this.files = files;
	}

	/** 조회수 증가 */
	public void increaseView() {
		this.viewCount++;
	}
	
}
