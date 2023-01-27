package com.son.facebookclone.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.son.facebookclone.dto.PostRequest;
import com.son.facebookclone.dto.PostResponse;
import com.son.facebookclone.service.UserPostService;
import com.son.facebookclone.utils.ErrorHelper;

@RestController
@RequestMapping("/api/v1/posts")
public class UserPostController {

	@Autowired
	private UserPostService service;	
	
	@GetMapping("/profile/{profileId}")
	public Object getPostsByProfile(@PathVariable("profileId") Long profileId) {
		List<PostResponse> userPosts = service.getPostByUserProfile(profileId);
		return new ResponseEntity<>(userPosts, HttpStatus.OK);
	}
	
	@GetMapping
	public Object getAllPostsOfFriends() {
		List<PostResponse> userPosts = service.getAllPostOfFriends();
		
		return new ResponseEntity<>(userPosts, HttpStatus.OK);
	}
	
	@PostMapping
	public Object savePost(@Valid @RequestBody PostRequest postRequest, 
							BindingResult result) {
		
		if (result.hasErrors())
			return new ResponseEntity<>(ErrorHelper.getErrorMessage(result), 
										HttpStatus.BAD_REQUEST);
		service.savePost(postRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("{postId}")
	public Object savePostImage(@PathVariable("postId") long postId, 
							@RequestParam("file") MultipartFile file) {
		service.addPostImage(postId, file);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/image/{postId}")
	public Object updatePostImage(@PathVariable("postId") long postId, 
							@RequestParam("file") MultipartFile file) {
		service.updateImage(postId, file);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("{postId}")
	public Object updatePost(@PathVariable("postId") long postId, 
							@Valid @RequestBody PostRequest postRequest, 
							BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(ErrorHelper.getErrorMessage(result), 
										HttpStatus.BAD_REQUEST);
		service.updatePost(postId, postRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public Object deletePost(@PathVariable("id") long id) {
		service.deletePost(id);
		
		return new ResponseEntity<>("Delete successfuly!", HttpStatus.OK);
	}
}
