package com.inhatc.spring.capstone.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.inhatc.spring.capstone.content.service.Article;

public interface ArticleRepository extends JpaRepository<com.inhatc.spring.capstone.content.service.Article, Integer> {
    
}
