package com.inhatc.spring.capstone.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.spring.capstone.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
