package com.example.authenticatingldap.exception;

public class InvalidResourceStateException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public InvalidResourceStateException(String msg) {
		super(msg);
	}

}
