package com.son.facebookclone.exceptions;

public class PostLikeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PostLikeNotFoundException(String message) {
		super(message);
	}
}
