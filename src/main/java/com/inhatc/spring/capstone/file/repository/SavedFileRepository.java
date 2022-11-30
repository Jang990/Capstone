package com.inhatc.spring.capstone.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.spring.capstone.file.entity.SavedFile;

public interface SavedFileRepository extends JpaRepository<SavedFile, Long>{

}
