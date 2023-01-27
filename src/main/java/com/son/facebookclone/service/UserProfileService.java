package com.son.facebookclone.service;

import java.util.List;

import com.son.facebookclone.dto.UserProfileDetailsDto;
import com.son.facebookclone.dto.UserProfileDto;

public interface UserProfileService {

	List<UserProfileDto> getAllFriends(long profileId);
	List<UserProfileDto> findAll(String keyword);
	UserProfileDetailsDto findById(long id);
	UserProfileDto getOwnProfile();
	void update(long id, UserProfileDto dto);
}
