package com.inhatc.spring.capstone.comment.service;

import org.springframework.stereotype.Service;

import com.inhatc.spring.capstone.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
}
