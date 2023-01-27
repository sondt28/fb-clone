package com.son.facebookclone.exceptions;

public class UserPostNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserPostNotFoundException(String message) {
		super(message);
	}
}
