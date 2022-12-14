package com.inhatc.spring.capstone.content.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.spring.capstone.content.entity.ContentHeart;

public interface ContentHeartRepository extends JpaRepository<ContentHeart, Long> {
	Optional<ContentHeart> findByContent_IdAndLikedUser_Email(Long id, String email);
	void  deleteByBoardEntity_IdAndMemberEntity_Id(Long id, String email);
}
