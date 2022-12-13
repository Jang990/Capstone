package com.inhatc.spring.capstone.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.spring.capstone.tag.entity.ContentTag;

public interface ContentTagRepository extends JpaRepository<ContentTag, Long> 
{
	
}
