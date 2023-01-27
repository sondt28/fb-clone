package com.son.facebookclone.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.son.facebookclone.exceptions.PostLikeException;
import com.son.facebookclone.exceptions.UserPostNotFoundException;
import com.son.facebookclone.model.PostLike;
import com.son.facebookclone.model.UserPost;
import com.son.facebookclone.model.UserProfile;
import com.son.facebookclone.repository.PostLikeRepository;
import com.son.facebookclone.repository.UserPostRepository;

@Service
public class PostLikeServiceImpl implements PostLikeService {
	
	@Autowired
	private UserPostRepository userPostRepository;
	
	@Autowired
	private PostLikeRepository repository;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public void save(long id) {
		UserProfile userProfile = authService.getCurrentUserProfile();
		
		UserPost userPost = userPostRepository.findById(id)
				.orElseThrow(() -> new UserPostNotFoundException("Post not found."));
		
		Optional<PostLike> postLikeOpt = repository
				.findByUserProfileAndUserPost(userProfile, userPost);
		
		if (postLikeOpt.isPresent())
			throw new PostLikeException("You have already liked this post.");
		
		PostLike postLike = new PostLike();
		postLike.setCreatedTime(LocalDateTime.now());
		postLike.setUserPost(userPost);
		postLike.setUserProfile(userProfile);
		
		repository.save(postLike);
	}

	@Override
	public void deleteByUserProfileAndUserPost(Long id) {
		UserPost userPost = userPostRepository.findById(id)
				.orElseThrow(() -> new UserPostNotFoundException("Post not found."));
		
		Optional<PostLike> postLike = repository
				.findByUserProfileAndUserPost(authService.getCurrentUserProfile(), userPost);
		
		if (postLike.isEmpty())
			throw new PostLikeException("The profile haven't liked this post.");
		
		repository.delete(postLike.get());
	}
}
