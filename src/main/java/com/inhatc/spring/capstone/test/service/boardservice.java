package com.inhatc.spring.capstone.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inhatc.spring.capstone.entity.board.board;
import com.inhatc.spring.capstone.test.repository.BoardRepository;

@Service
public class boardservice {

	@Autowired
	private BoardRepository boardRepository;
	
	public void write(board board) {
		boardRepository.save(board);	
		}
}
