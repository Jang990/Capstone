package com.inhatc.spring.capstone.entity.Tag;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.inhatc.spring.capstone.entity.recuit.RecruitedMember;

@Entity
@Table(name = "position_tag")
public class PositionTag {
	@Id
	@Column(name = "language_tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "recruited_id")
	RecruitedMember member;
	
	@ManyToOne
	@JoinColumn(name = "tag_id")
	Tag tag;
}