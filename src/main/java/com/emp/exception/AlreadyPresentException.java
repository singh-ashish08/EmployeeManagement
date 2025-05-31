package com.emp.exception;

public class AlreadyPresentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AlreadyPresentException(String message) {
		super(message);
	}

	public AlreadyPresentException(String message, Throwable cause) {
		super(message, cause);
	}

}
