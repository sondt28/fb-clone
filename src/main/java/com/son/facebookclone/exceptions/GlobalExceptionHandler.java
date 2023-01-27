package com.son.facebookclone.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public Object GlobalHandlerException(Exception exc) {
		Map<String, Object> map = new HashMap<>();
		map.put("timeStamp", LocalDateTime.now());
		map.put("error", exc.getMessage());
		map.put("status", HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public Object UserProfileHandlerException(UserProfileNotFoundException exc) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("timeStamp", LocalDateTime.now());
		map.put("error", exc.getMessage());
		map.put("status", HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public Object UserPostHandlerException(UserPostNotFoundException exc) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("timeStamp", LocalDateTime.now());
		map.put("error", exc.getMessage());
		map.put("status", HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public Object PostLikeHandlerException(PostLikeNotFoundException exc) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("timeStamp", LocalDateTime.now());
		map.put("error", exc.getMessage());
		map.put("status", HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public Object PostCommentHandlerException(PostCommentNotFoundException exc) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("timeStamp", LocalDateTime.now());
		map.put("error", exc.getMessage());
		map.put("status", HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public Object PostLikeHandlerException(PostLikeException exc) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("timeStamp", LocalDateTime.now());
		map.put("error", exc.getMessage());
		map.put("status", HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public Object FriendshipHandlerException(FriendshipException exc) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("timeStamp", LocalDateTime.now());
		map.put("error", exc.getMessage());
		map.put("status", HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public Object UserPostHandlerException(UserPostException exc) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("timeStamp", LocalDateTime.now());
		map.put("error", exc.getMessage());
		map.put("status", HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public Object UserPostImageHandlerException(UserPostImageException exc) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("timeStamp", LocalDateTime.now());
		map.put("error", exc.getMessage());
		map.put("status", HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
	}
}
