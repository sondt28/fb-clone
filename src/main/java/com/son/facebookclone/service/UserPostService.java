package com.son.facebookclone.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.son.facebookclone.dto.PostRequest;
import com.son.facebookclone.dto.PostResponse;

public interface UserPostService {
	
	void savePost(PostRequest postRequest);
	void updatePost(long postId, PostRequest postRequest);
	void deletePost(long postId);
	PostResponse getPostById(long postId);
	List<PostResponse> getPostByUserProfile(Long profileId);
	List<PostResponse> getAllPostOfFriends();
}
