package com.inhatc.spring.capstone.tag.entity;

import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.inhatc.spring.capstone.tag.constant.TagType;
import com.inhatc.spring.capstone.tag.dto.DisplayedTagDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table(name="tag")
/** 커스텀(사용자가 알아서 만든 태그), Tech(Java, Springboot, Bootstrap 등등)를 모아둔 엔티티  */
public class Tag {
	@Id
	@Column(name = "tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TagType type;
	
	@Column(unique = true, nullable = false)
	private String name;
	
	@Column(nullable = false)
	private int taggedCount;
	
	public static Tag createCustomTag(String tagName) {
		return Tag.builder()
				.type(TagType.CUSTOM)
				.name(tagName)
				.taggedCount(1)
				.build();
	}
	
	@Builder
	private Tag(Long id, TagType type, String name, int taggedCount) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.taggedCount = taggedCount;
	}
	
}
