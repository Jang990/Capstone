package com.inhatc.spring.capstone.user.exception;

public class UsersException extends RuntimeException {
	private final UserErrorMessage errorMessage;
    
    public UsersException(UserErrorMessage errorMessage, String userId) {
    	super(errorMessage.getMessage() + "\n" + "사용자ID: " + userId);
    	this.errorMessage = errorMessage;
    }
    
}
