package com.valeria.services.exceptions;

public class InsufficientFundsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InsufficientFundsException(String message) {
		super(message);
	}
	
	public InsufficientFundsException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
