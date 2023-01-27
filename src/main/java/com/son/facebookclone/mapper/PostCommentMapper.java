package com.son.facebookclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.son.facebookclone.dto.CommentDto;
import com.son.facebookclone.dto.CommentResponseDto;
import com.son.facebookclone.model.PostComment;
import com.son.facebookclone.model.UserPost;
import com.son.facebookclone.model.UserProfile;

@Mapper
public interface PostCommentMapper {

	PostCommentMapper INSTANCE = Mappers.getMapper(PostCommentMapper.class);
	
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "createdDateTime", source = "dto.createdDateTime")
	@Mapping(target = "userPost", source = "userPost")
	@Mapping(target = "userProfile", source = "userProfile")
	PostComment commentDtoToPostComment(CommentDto dto, UserProfile userProfile, UserPost userPost);

	CommentResponseDto commentToCommentDto(PostComment postComment);

}
