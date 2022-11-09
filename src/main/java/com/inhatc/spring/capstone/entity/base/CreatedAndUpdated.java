package com.inhatc.spring.capstone.entity.base;

import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public class CreatedAndUpdated {
	@CreationTimestamp
	private Timestamp date_created;  // 등록 시간
	@UpdateTimestamp
	private Timestamp last_updated; // 수정 시간
}
