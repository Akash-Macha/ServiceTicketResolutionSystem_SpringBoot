package com.comakeit.strs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class STRSUserNotAddedExcpetion extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public STRSUserNotAddedExcpetion() {
		super();
	}

	public STRSUserNotAddedExcpetion(String message) {
		super(message);
	}	
}
