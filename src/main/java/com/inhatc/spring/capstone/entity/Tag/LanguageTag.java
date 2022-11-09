package com.inhatc.spring.capstone.entity.Tag;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.inhatc.spring.capstone.entity.content.Content;

@Entity
@Table(name = "language_tag")
/** Java, C#, C++ 등의 언어 태그 엔티티 */
public class LanguageTag {
	@Id
	@Column(name = "language_tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "project_content_id")
	Content project;
	
	@ManyToOne
	@JoinColumn(name = "tag_id")
	Tag tag;
}
