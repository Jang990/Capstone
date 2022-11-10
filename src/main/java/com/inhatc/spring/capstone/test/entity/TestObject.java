package com.inhatc.spring.capstone.test.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TestObject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long testId;
	private String testStr;
	
	@Builder
	public TestObject(String testStr) {
		this.testStr = testStr;
	}
}
