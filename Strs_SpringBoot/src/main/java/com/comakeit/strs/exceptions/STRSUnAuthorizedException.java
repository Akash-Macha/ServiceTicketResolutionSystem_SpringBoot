package com.comakeit.strs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class STRSUnAuthorizedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public STRSUnAuthorizedException() {
		super();
	}

	public STRSUnAuthorizedException(String message) {
		super(message);
	}	
}
