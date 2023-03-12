package com.inhatc.spring.capstone.users;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.inhatc.spring.capstone.user.entity.Users;
import com.inhatc.spring.capstone.user.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.inhatc.spring.capstone.user.dto.DisplayedUserDTO;
import com.inhatc.spring.capstone.user.dto.UsersJoinDTO;
import com.inhatc.spring.capstone.user.exception.UserErrorDescription;
import com.inhatc.spring.capstone.user.exception.UsersException;
import com.inhatc.spring.capstone.user.service.UserService;

import java.util.Optional;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private UsersRepository usersRepository;
	@InjectMocks
	private UserService userService;
	UsersJoinDTO usersJoin;
	Users user;
	
	UsersJoinDTO createUsersJoinDTO() {
		String email = "aaa@test.com";
		String password = "aaa";
		String name = "홍길동";
		return new UsersJoinDTO(email, name, password);
	}

	@BeforeEach
	void beforeEach() {
		usersJoin = createUsersJoinDTO();
		user = Users.createUser(usersJoin);
	}

	@Test
	@DisplayName("정상 회원가입")
	void joinUser() {
		// given
		when(usersRepository.findByEmail(any())).thenReturn(Optional.ofNullable(null));
		when(usersRepository.save(any())).thenReturn(user);

		// when
		DisplayedUserDTO displayedUser = userService.joinUser(usersJoin);

		//then
		assertEquals(usersJoin.getName(), displayedUser.getName());
		assertEquals(usersJoin.getEmail(), displayedUser.getEmail());
	}
	
	@Test
	@DisplayName("회원가입시 회원 ID 중복")
	void duplicateUser() {
		// given
		when(usersRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));

		//when
		UsersException e = assertThrows(UsersException.class, () -> {userService.joinUser(usersJoin);});

		//then
		assertEquals(UserErrorDescription.DUPLICATED_USER_ID, e.getErrorDescription());
	}
	
	@Test
	@DisplayName("이메일로 사용자 조회")
	void searchUser() {
		// given
		when(usersRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));

		// when
		DisplayedUserDTO foundUser = userService.searchUser(usersJoin.getEmail());

		// then
		assertEquals(usersJoin.getEmail(), foundUser.getEmail());
		assertEquals(usersJoin.getName(), foundUser.getName());
	}
	
	@Test
	@DisplayName("이메일로 사용자 조회 실패")
	void searchUserFail() {
		// given
		when(usersRepository.findByEmail(any())).thenReturn(Optional.ofNullable(null));

		// when
		UsersException e = assertThrows(UsersException.class, () -> {userService.searchUser("UnknownEmail");});

		// then
		assertEquals(UserErrorDescription.NOT_FOUND_USER, e.getErrorDescription());
	}

}
