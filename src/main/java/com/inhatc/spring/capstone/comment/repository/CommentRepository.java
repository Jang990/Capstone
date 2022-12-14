package com.inhatc.spring.capstone.comment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.spring.capstone.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Optional<Comment> findByContent_IdAndWriter_Email(Long contentId, String email);
}
