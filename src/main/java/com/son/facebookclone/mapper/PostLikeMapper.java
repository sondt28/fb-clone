package com.son.facebookclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.son.facebookclone.dto.LikeDto;
import com.son.facebookclone.model.PostLike;

@Mapper
public interface PostLikeMapper {

	PostLikeMapper INSTANCE = Mappers.getMapper(PostLikeMapper.class);
	
	@Mapping(target = "givenName", source = "userProfile.givenName")
	@Mapping(target = "surname", source = "userProfile.surname")
	@Mapping(target = "profileId", source = "userProfile.id")
	LikeDto postLikeToLikeDto(PostLike postLike);
}
