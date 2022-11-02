package com.inhatc.spring.capstone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.spring.capstone.testentity.TestObject;

public interface TestObjectRespository extends JpaRepository<TestObject, Long> {
	Optional<TestObject> findByTestId(Long testId);
}
