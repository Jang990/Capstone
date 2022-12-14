package com.inhatc.spring.capstone.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.inhatc.spring.capstone.content.entity.UploadFile;

public interface FileRepository extends JpaRepository<UploadFile, Integer> {
    public UploadFile findOneByFileName(String fileName);
}
