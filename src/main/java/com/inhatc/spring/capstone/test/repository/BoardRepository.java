package com.inhatc.spring.capstone.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inhatc.spring.capstone.entity.board.board;

@Repository
public interface BoardRepository extends JpaRepository<board,Integer>{

}
