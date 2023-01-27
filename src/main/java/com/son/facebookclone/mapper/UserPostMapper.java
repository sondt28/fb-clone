package com.son.facebookclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import com.son.facebookclone.dto.PostRequest;
import com.son.facebookclone.dto.PostResponse;
import com.son.facebookclone.model.UserPost;
import com.son.facebookclone.model.UserProfile;
import com.son.facebookclone.repository.PostCommentRepository;
import com.son.facebookclone.repository.PostLikeRepository;

@Mapper(componentModel = "spring")
public abstract class UserPostMapper {
	
	@Autowired
	private PostLikeRepository postLikeRepository;
	
	@Autowired
	private PostCommentRepository postCommentRepository;
	 
	public static UserPostMapper INSTANCE = Mappers.getMapper(UserPostMapper.class);
	
	@Mapping(target = "userProfile", source = "userProfile")
	@Mapping(target = "id", source = "postRequest.id")
	public abstract UserPost postRequestToPost(PostRequest postRequest, UserProfile userProfile);
	
	@Mapping(target = "givenName", source = "userProfile.givenName")
	@Mapping(target = "surname", source = "userProfile.surname")
	@Mapping(target = "totalComment", expression = "java(getTotalComment(userPost))")
	@Mapping(target = "totalLike", expression = "java(getTotalLike(userPost))")
	@Mapping(target = "photo", source = "image.path")
	public abstract PostResponse postToPostResponse(UserPost userPost);
	
	public Integer getTotalLike(UserPost post) {
		return postLikeRepository.totalLikeOfPost(post.getId());
	}
	
	public Integer getTotalComment(UserPost post) {
		return postCommentRepository.totalCommentOfPost(post.getId());
	}
}
