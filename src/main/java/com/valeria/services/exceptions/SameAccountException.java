package com.valeria.services.exceptions;

public class SameAccountException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SameAccountException(String message) {
		super(message);
	}
	
	public SameAccountException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
