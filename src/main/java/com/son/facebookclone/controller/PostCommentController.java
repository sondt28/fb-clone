package com.son.facebookclone.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.son.facebookclone.dto.CommentDto;
import com.son.facebookclone.service.PostCommentService;
import com.son.facebookclone.utils.ErrorHelper;

@RestController
@RequestMapping("/api/v1/comments")
public class PostCommentController {
	
	@Autowired
	private PostCommentService service;
	
	@PostMapping("{postId}")
	public Object saveComment(@PathVariable("postId") Long postId,
							@Valid @RequestBody CommentDto commentDto, 
							BindingResult result) {
		
		if (result.hasErrors())
			return new ResponseEntity<>(ErrorHelper.getErrorMessage(result), 
										HttpStatus.BAD_REQUEST);
		
		service.save(postId, commentDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("{postId}")
	public Object removeComment(@PathVariable("postId") Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
