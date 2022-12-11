package com.inhatc.spring.capstone.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.spring.capstone.tag.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {
	
}
