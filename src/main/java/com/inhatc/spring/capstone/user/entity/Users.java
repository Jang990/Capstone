package com.inhatc.spring.capstone.user.entity;

import java.util.ArrayList; 
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
/** 사용자 엔티티 */
public class Users {
	/*
	사용자 번호 - PK
	이름 
	비밀번호 
	권한 
	이메일 - UNI
	 */
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String password;
	
	@Column(unique = true)
	private String email;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<UserRole> role = new ArrayList<>();
	
}
