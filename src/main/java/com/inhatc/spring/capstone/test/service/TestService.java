package com.inhatc.spring.capstone.test.service;

import org.springframework.stereotype.Service;

import com.inhatc.spring.capstone.test.dto.TestDTO;
import com.inhatc.spring.capstone.test.entity.TestObject;
import com.inhatc.spring.capstone.test.repository.TestObjectRespository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {
	
	private final TestObjectRespository testRespository;
	
	public TestDTO getTest(Long id) {
		TestObject test = testRespository.findByTestId(id).orElseGet(() -> new TestObject("임시 테스트 문자열"));
		return new TestDTO(test.getTestStr());
	}
	
	public void saveTest(TestDTO testDTO) {
		testRespository.save(new TestObject(testDTO.getTestStr()));
	}
}
