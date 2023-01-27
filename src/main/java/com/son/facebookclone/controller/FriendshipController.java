package com.son.facebookclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.son.facebookclone.service.FriendshipService;

@RestController
@RequestMapping("/api/v1/friendships")
public class FriendshipController {

	@Autowired
	private FriendshipService service;
	
	@PostMapping("/add-friend/{userProfileId}")
	public Object saveFriendships(@PathVariable("userProfileId") Long userProfileId) {
		service.saveFriendship(userProfileId);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/unfriend/{userProfileId}")
	public Object removeFriendships(@PathVariable("userProfileId") Long userProfileId) {
		service.removeFriendship(userProfileId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
