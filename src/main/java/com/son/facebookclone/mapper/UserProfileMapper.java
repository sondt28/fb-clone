package com.son.facebookclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.son.facebookclone.dto.RegisterDto;
import com.son.facebookclone.dto.UserProfileDetailsDto;
import com.son.facebookclone.dto.UserProfileDto;
import com.son.facebookclone.model.UserProfile;

@Mapper
public interface UserProfileMapper {

	UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);
	
	UserProfile toModel(RegisterDto dto);
	RegisterDto toDto(UserProfile model);
	UserProfileDto toUserProfileDto(UserProfile model);
	UserProfileDetailsDto toUserProfileDetailsDto(UserProfile model);
}
