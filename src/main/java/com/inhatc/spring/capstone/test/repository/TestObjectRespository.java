package com.inhatc.spring.capstone.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.spring.capstone.test.entity.TestObject;

public interface TestObjectRespository extends JpaRepository<TestObject, Long> {
	Optional<TestObject> findByTestId(Long testId);
}
