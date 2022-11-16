package com.inhatc.spring.capstone.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.inhatc.spring.capstone.user.constant.Roles;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
/** 권한 엔티티 */
public class Role {
	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Roles role;
	
	public static Role createUserRole() {
		return new Role(Roles.USER); 
	}
	
	public static Role createAdminRole() {
		return new Role(Roles.ADMIN); 
	}
	
	private Role(Roles role) {
		this.role = role;
	}
	
}
