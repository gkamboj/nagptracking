package com.nagarro.nagptrackingsystem.exceptions;

public class NoSuchElementException extends Exception {

	private static final long serialVersionUID = 1L;
	String message;

	public NoSuchElementException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
