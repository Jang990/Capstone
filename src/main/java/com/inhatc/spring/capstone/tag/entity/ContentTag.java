package com.inhatc.spring.capstone.tag.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.inhatc.spring.capstone.content.entity.Content;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Content_Tag", uniqueConstraints = {
//		@UniqueConstraint(
//				columnNames = {"content_id", "tag_id"}
//		)
})
/** 
 * 컨텐츠에 어떤 태그들이 포함되어 있는지 확인하는 엔티티 
 * Content - Content에 포함된 태그들 - 태그
 * 중간 테이블 역할
 */
public class ContentTag {
	@Id
	@Column(name = "content_tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	Content content;
	
	@ManyToOne
	@JoinColumn(name = "tag_id")
	Tag tag;

	public ContentTag(Content project, Tag tag) {
		this.content = project;
		this.tag = tag;
	}
	
}
