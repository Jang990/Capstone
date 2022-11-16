package com.inhatc.spring.capstone.user.service;

import org.springframework.stereotype.Service;

import com.inhatc.spring.capstone.user.dto.DisplayedUserDTO;
import com.inhatc.spring.capstone.user.dto.UsersJoinDTO;
import com.inhatc.spring.capstone.user.entity.Users;
import com.inhatc.spring.capstone.user.exception.UserErrorDescription;
import com.inhatc.spring.capstone.user.exception.UsersException;
import com.inhatc.spring.capstone.user.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UsersRepository userRepository;
	
	/** 사용자 회원 가입 */ 
	public DisplayedUserDTO joinUser(UsersJoinDTO usersJoin) {
		String id = usersJoin.getEmail();
		if(userRepository.findByEmail(id).isPresent()) {
			throw new UsersException(UserErrorDescription.DUPLICATED_USER_ID, id);
		}
		
		Users user = Users.createUser(usersJoin);
		user = userRepository.save(user);
		return new DisplayedUserDTO(user.getName(), user.getEmail());
	}
	
}
