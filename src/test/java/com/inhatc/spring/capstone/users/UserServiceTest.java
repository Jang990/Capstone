package com.inhatc.spring.capstone.users;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inhatc.spring.capstone.user.dto.DisplayedUserDTO;
import com.inhatc.spring.capstone.user.dto.UsersJoinDTO;
import com.inhatc.spring.capstone.user.exception.UserErrorDescription;
import com.inhatc.spring.capstone.user.exception.UsersException;
import com.inhatc.spring.capstone.user.service.UserService;

@SpringBootTest
class UserServiceTest {
	@Autowired
	private UserService userService;
	
	
	UsersJoinDTO createUsersJoinDTO() {
		String email = "aaa@test.com";
		String password = "aaa";
		String name = "홍길동";
		return new UsersJoinDTO(email, name, password);
	}
	
	@Test
	@Transactional
	@DisplayName("정상 회원가입")
	void joinUser() {
		UsersJoinDTO usersJoin = createUsersJoinDTO();
		
		DisplayedUserDTO displayedUser = userService.joinUser(usersJoin);
		
		System.out.println(displayedUser);
		assertEquals(displayedUser.getName(), usersJoin.getName());
		assertEquals(displayedUser.getEmail(), usersJoin.getEmail());
	}
	
	@Test
	@Transactional
	@DisplayName("회원가입시 회원 ID 중복")
	void duplicateUser() {
		UsersJoinDTO usersJoin = createUsersJoinDTO();
		
		userService.joinUser(usersJoin);
		
		UsersException e = assertThrows(UsersException.class, () -> {userService.joinUser(usersJoin);});
		System.out.println(e.getMessage());
		assertEquals(UserErrorDescription.DUPLICATED_USER_ID, e.getErrorDescription());
	}

}
