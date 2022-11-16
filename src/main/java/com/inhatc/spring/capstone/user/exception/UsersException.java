package com.inhatc.spring.capstone.user.exception;

import lombok.Getter;

@Getter
public class UsersException extends RuntimeException {
	private final UserErrorDescription errorDescription;
    
    public UsersException(UserErrorDescription errorDescription, String userId) {
    	super(errorDescription.getMessage() + "\n" + "사용자ID: " + userId);
    	this.errorDescription = errorDescription;
    }
    
}
