package com.inhatc.spring.capstone.content.exception;

import lombok.Getter;

@Getter
public class ContentException extends RuntimeException {
	private final ContentErrorDescription errorDescription;
    
    public ContentException(ContentErrorDescription errorDescription, Long contentId) {
    	super(errorDescription.getMessage() + "\n" + "게시글 ID: " + contentId);
    	this.errorDescription = errorDescription;
    }
    
}
