package com.son.facebookclone.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.son.facebookclone.model.UserPost;
import com.son.facebookclone.model.UserPostImage;

@DataJpaTest
public class UserPostImageRepositoryTest {

	@Autowired
	private UserPostImageRepository repository;
	
	@Autowired
	private UserPostRepository postRepository;
	
	private UserPost userPost;
	
	private UserPostImage userPostImage;
	
	@Test
	void shouldSavePostImage() {
		userPostImage = new UserPostImage();
		userPostImage.setPath("fdasfdsa");
		userPost = new UserPost();
		userPost.setImage(userPostImage);
		userPostImage.setUserPost(userPost);
		
		postRepository.save(userPost);
		
		UserPostImage actual = repository.save(userPostImage);
		List<UserPostImage> lists = repository.findAll();
		
		assertEquals(1, lists.size());
		assertEquals(userPostImage.getPath(), actual.getPath());
		assertEquals(userPostImage.getUserPost(), actual.getUserPost());
	}
	
	@Test
	void shouldUpdatePostImage() {
		userPostImage = new UserPostImage();
		userPostImage.setPath("fdasfdsa");
		userPost = new UserPost();
		userPost.setImage(userPostImage);
		userPostImage.setUserPost(userPost);
		
		postRepository.save(userPost);
		repository.save(userPostImage);
		
		userPostImage.setPath("dasjfnjas");
		repository.save(userPostImage);
		
		UserPostImage actual = repository.findById(userPostImage.getId()).orElse(null);
		
		assertEquals(userPostImage.getPath(), actual.getPath());
	}
	
	@Test
	void shouldDeletePostImage() {
		userPostImage = new UserPostImage();
		userPostImage.setPath("fdasfdsa");
		userPost = new UserPost();
		userPost.setImage(userPostImage);
		userPostImage.setUserPost(userPost);
		
		repository.save(userPostImage);
		repository.delete(userPostImage);
		List<UserPostImage> lists = repository.findAll();
		assertEquals(0, lists.size());
	}
	
	@Test
	void shouldDeletePostImageOfPost() {
		userPostImage = new UserPostImage();
		userPostImage.setPath("fdasfdsa");
		userPost = new UserPost();
		userPost.setImage(userPostImage);
		userPostImage.setUserPost(userPost);
		repository.save(userPostImage);
		
		UserPost actual = postRepository.save(userPost);
		
		postRepository.delete(actual);
		
		List<UserPostImage> images = repository.findAll();
		assertEquals(0, images.size());
	}
}
