package com.inhatc.spring.capstone.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inhatc.spring.capstone.content.entity.Content;

public interface ContentRepository extends JpaRepository<Content, Long>, ContentRepositoryCustom {
	
}
