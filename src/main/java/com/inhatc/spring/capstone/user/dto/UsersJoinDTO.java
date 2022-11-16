package com.inhatc.spring.capstone.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/** 회원가입을 위한 정보 */
public class UsersJoinDTO {
	private String email;
	private String name;
	private String password;
}
