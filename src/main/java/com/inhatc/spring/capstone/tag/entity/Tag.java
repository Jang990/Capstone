package com.inhatc.spring.capstone.tag.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.inhatc.spring.capstone.tag.constant.TagType;

@Entity
@Table(name="tag")
/** 분야(네트워크, 웹), 언어(Java,C#), 포지션(백엔드, 프론트엔드)를 모아둔 엔티티  */
public class Tag {
	@Id
	@Column(name = "tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private TagType type;
	private String name;
	private int taggedCount;
}
