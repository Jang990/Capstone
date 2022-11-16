package com.inhatc.spring.capstone.user.entity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.inhatc.spring.capstone.user.dto.UsersJoinDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
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
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="User_Role", 
		joinColumns = {@JoinColumn(name = "user_id")},
		inverseJoinColumns = {@JoinColumn(name = "role_id")}
	)
	private Set<Role> roles = new LinkedHashSet<>();
	
	public static Users createUser(UsersJoinDTO usersJoin) {
		return Users.builder()
				.name(usersJoin.getName())
				.password(usersJoin.getPassword())
				.email(usersJoin.getEmail())
				.role(Role.createUserRole())
				.build();
	}


	@Builder
	private Users(String name, String password, String email, Role role) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.roles.add(role);
	}
	
}
