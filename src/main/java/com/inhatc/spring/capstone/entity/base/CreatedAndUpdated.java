package com.inhatc.spring.capstone.entity.base;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;

@MappedSuperclass
@Getter
public class CreatedAndUpdated {
	@CreationTimestamp
	private LocalDateTime date_created;  // 등록 시간
	@UpdateTimestamp
	private LocalDateTime last_updated; // 수정 시간
}
