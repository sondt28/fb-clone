package com.son.facebookclone.exceptions;

public class PostCommentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PostCommentNotFoundException(String message) {
		super(message);
	}
}
