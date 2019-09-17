package com.comakeit.strs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class STRSNoContentException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public STRSNoContentException() {
		super();
	}

	public STRSNoContentException(String message) {
		super(message);
	}	
}
