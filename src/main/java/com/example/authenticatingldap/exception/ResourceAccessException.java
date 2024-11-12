package com.example.authenticatingldap.exception;

public class ResourceAccessException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ResourceAccessException(String message) {
		super(message);
	}

}
