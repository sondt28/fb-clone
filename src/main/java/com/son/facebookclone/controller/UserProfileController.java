package com.son.facebookclone.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.son.facebookclone.dto.UserProfileDetailsDto;
import com.son.facebookclone.dto.UserProfileDto;
import com.son.facebookclone.service.UserProfileService;
import com.son.facebookclone.utils.ErrorHelper;

@RestController
@RequestMapping("/api/v1/profiles")
public class UserProfileController {

	@Autowired
	private UserProfileService service;
	
	@GetMapping("friends/{profileId}")
	public Object getAllFriends(@PathVariable("profileId") long profileId) {
		List<UserProfileDto> dtos = service.getAllFriends(profileId);
		
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	@GetMapping
	public Object getOwnProfile() {
		UserProfileDto dto = service.getOwnProfile();
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public Object findProfileById(@PathVariable("id") long id) {
		UserProfileDetailsDto dto = service.findById(id);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping("/search/{keyword}")
	public Object findProfilesByKeyWord(@PathVariable("keyword") String keyword) {
		List<UserProfileDto> dtos = service.findAll(keyword);
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public Object update(@PathVariable("id") long id, 
						@Valid @RequestBody UserProfileDto dto, 
						BindingResult result) {
		
		if (result.hasErrors())
			return new ResponseEntity<>(ErrorHelper.getErrorMessage(result), 
										HttpStatus.BAD_REQUEST);
		
		service.update(id, dto);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
