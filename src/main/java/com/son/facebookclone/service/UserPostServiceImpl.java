package com.son.facebookclone.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.son.facebookclone.dto.PostRequest;
import com.son.facebookclone.dto.PostResponse;
import com.son.facebookclone.exceptions.UserPostException;
import com.son.facebookclone.exceptions.UserPostImageException;
import com.son.facebookclone.exceptions.UserPostNotFoundException;
import com.son.facebookclone.exceptions.UserProfileNotFoundException;
import com.son.facebookclone.mapper.UserPostMapper;
import com.son.facebookclone.model.UserPost;
import com.son.facebookclone.model.UserPostImage;
import com.son.facebookclone.model.UserProfile;
import com.son.facebookclone.repository.UserPostImageRepository;
import com.son.facebookclone.repository.UserPostRepository;
import com.son.facebookclone.repository.UserProfileRepository;
import com.son.facebookclone.utils.FileUploadHelper;

@Service
public class UserPostServiceImpl implements UserPostService {

	@Autowired
	private UserPostRepository userPostRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private UserPostImageRepository userPostImageRepository;
	
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
		
		if (post.getImage() != null) 
			FileUploadHelper.deleteFile(post.getId(), post.getImage().getPath());
	}

	@Override
	public void savePost(PostRequest postRequest) {
		postRequest.setCreatedDateTime(LocalDateTime.now());
		UserPost post = UserPostMapper.INSTANCE.postRequestToPost(postRequest, 
											authService.getCurrentUserProfile());
	
		userPostRepository.save(post);
	}

	@Override
	public void addPostImage(long postId, MultipartFile file) {
		UserPost post = userPostRepository.findById(postId)
				.orElseThrow(() -> new UserPostNotFoundException("Post not found!"));
		
		if (post.getImage() == null) 
			saveImage(file, post);
		else 
			throw new UserPostImageException("Post can only add one photo.");
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

	@Override
	public void updateImage(long postId, MultipartFile file) {
		UserPost post = userPostRepository.findById(postId)
				.orElseThrow(() -> new UserPostNotFoundException("Post not found!"));
		
		if (post.getUserProfile() != authService.getCurrentUserProfile())
			throw new UserPostException("You are not owner of this post!");
		
		if (post.getImage() != null) {
			try {
				FileUploadHelper.updateFile(post.getId(), post.getImage().getPath(), file);
				
				UserPostImage image = userPostImageRepository
							.findById(post.getImage().getId())
							.orElseThrow(() -> new UserPostImageException("image not found."));
				
				image.setPath(file.getOriginalFilename());
				
				post.setImage(image);
				
				userPostRepository.save(post);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			saveImage(file, post);
		}	
	}

	private void saveImage(MultipartFile file, UserPost post) {
		try {
			FileUploadHelper.saveFile(post.getId(), file);
			
			UserPostImage image = new UserPostImage();
			image.setPath(file.getOriginalFilename());
			
			post.setImage(image);
			userPostRepository.save(post);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
