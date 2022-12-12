package com.inhatc.spring.capstone.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.spring.capstone.tag.entity.Tag;
import java.lang.String;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {
	Optional<Tag> findByName(String name);
}
