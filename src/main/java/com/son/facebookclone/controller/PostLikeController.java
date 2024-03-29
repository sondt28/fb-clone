package com.son.facebookclone.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.son.facebookclone.service.PostLikeService;

@RestController
@RequestMapping("/api/v1/likes")
public class PostLikeController {
	
	@Autowired
	private PostLikeService service;
	
	@PostMapping("{postId}")
	public Object saveLike(@PathVariable("postId") Long postId) {
		service.save(postId);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("{id}")
	public Object removeLike(@PathVariable("id") Long id) {
		
		service.deleteByUserProfileAndUserPost(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
