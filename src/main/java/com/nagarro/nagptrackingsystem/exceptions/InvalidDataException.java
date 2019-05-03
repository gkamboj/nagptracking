package com.nagarro.nagptrackingsystem.exceptions;

public class InvalidDataException extends Exception {

	private static final long serialVersionUID = 1L;
	String message;

	public InvalidDataException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
