package com.son.facebookclone.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.son.facebookclone.dto.PostRequest;
import com.son.facebookclone.dto.PostResponse;
import com.son.facebookclone.exceptions.UserPostException;
import com.son.facebookclone.exceptions.UserPostNotFoundException;
import com.son.facebookclone.exceptions.UserProfileNotFoundException;
import com.son.facebookclone.mapper.UserPostMapper;
import com.son.facebookclone.model.UserPost;
import com.son.facebookclone.model.UserProfile;
import com.son.facebookclone.repository.UserPostRepository;
import com.son.facebookclone.repository.UserProfileRepository;

@Service
public class UserPostServiceImpl implements UserPostService {

	@Autowired
	private UserPostRepository userPostRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private UserPostMapper postMapper;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public PostResponse getPostById(long postId) {
		UserPost userPost = userPostRepository.findById(postId)
				.orElseThrow(()-> new UserPostNotFoundException("Post not found."));
		PostResponse postResponse = postMapper.postToPostResponse(userPost);
		return postResponse;
	}

	@Override
	public List<PostResponse> getAllPostOfFriends()  {
		Long userProfileId = authService.getCurrentUserProfile().getId();
		
		List<UserPost> postsOfProfileRequest = userPostRepository
							.findAllPostByListFriendOfUserProfileRequest(userProfileId);
		
		List<UserPost> postsOfProfileAccept = userPostRepository
				.findAllPostByListFriendOfUserProfileAccept(userProfileId);
		
		List<PostResponse> dtos = getPostByOwnUserProfile();
	
		dtos.addAll(postsOfProfileRequest.stream()
				.map(t -> postMapper.postToPostResponse(t))
				.collect(Collectors.toList()));
		
		dtos.addAll(postsOfProfileAccept.stream()
				.map(t -> postMapper.postToPostResponse(t))
				.collect(Collectors.toList()));
		
		return dtos;
	}

	@Override
	public List<PostResponse> getPostByUserProfile(Long profileId) {
		UserProfile userProfile = userProfileRepository.findById(profileId)
					.orElseThrow(() -> new UserProfileNotFoundException("Profile not found"));
		
		List<UserPost> posts = userPostRepository.findByUserProfile(userProfile, 
														Sort.by(Sort.Direction.DESC, "id"));
		
		List<PostResponse> dtos = posts.stream()
										.map(t -> postMapper.postToPostResponse(t))
										.collect(Collectors.toList());
		
		return dtos;
	}

	@Override
	public void deletePost(long id) {
		UserPost post = userPostRepository.findById(id)
				.orElseThrow(() -> new UserPostNotFoundException("Post not found!"));
		
		if (post.getUserProfile() != authService.getCurrentUserProfile())
			throw new UserPostException("You are not owner of this post!");
		
		userPostRepository.delete(post);
	}

	@Override
	public void savePost(PostRequest postRequest) {
		postRequest.setCreatedDateTime(LocalDateTime.now());
		UserPost post = UserPostMapper.INSTANCE.postRequestToPost(postRequest, 
											authService.getCurrentUserProfile());
	
		userPostRepository.save(post);
	}


	@Override
	public void updatePost(long postId, PostRequest postRequest) {
		UserPost post = userPostRepository.findById(postId)
				.orElseThrow(() -> new UserPostNotFoundException("Post not found!"));
		
		if (post.getUserProfile() != authService.getCurrentUserProfile())
			throw new UserPostException("You are not owner of this post!");
		
		post.setWrittenText(postRequest.getWrittenText());
		userPostRepository.save(post);
		
	}
	
	private List<PostResponse> getPostByOwnUserProfile() {
		List<UserPost> posts = userPostRepository
								.findByUserProfile(authService.getCurrentUserProfile(), 
													Sort.by(Sort.Direction.DESC, "id"));
		
		List<PostResponse> postsResponse = posts.stream()
											.map(t -> postMapper.postToPostResponse(t))
											.collect(Collectors.toList());
		return postsResponse;
	}
}
