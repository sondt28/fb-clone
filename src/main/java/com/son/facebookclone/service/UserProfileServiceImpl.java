package com.son.facebookclone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.son.facebookclone.dto.UserProfileDetailsDto;
import com.son.facebookclone.dto.UserProfileDto;
import com.son.facebookclone.exceptions.UserProfileNotFoundException;
import com.son.facebookclone.mapper.UserProfileMapper;
import com.son.facebookclone.model.UserProfile;
import com.son.facebookclone.repository.UserProfileRepository;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserProfileRepository repository;
	
	@Override
	public List<UserProfileDto> getAllFriends(long profileId) {
		
		List<UserProfile> userProfileRequest = repository.findByOwnUserProfileRequest(profileId); 
		List<UserProfile> userProfileAccept = repository.findByOwnUserProfileAccept(profileId);
		List<UserProfileDto> userProfilesDto = new ArrayList<>();
		
		userProfilesDto.addAll(userProfileRequest.stream()
								.map(t -> UserProfileMapper.INSTANCE.toUserProfileDto(t))
								.collect(Collectors.toList()));
								
		userProfilesDto.addAll(userProfileAccept.stream()
								.map(t -> UserProfileMapper.INSTANCE.toUserProfileDto(t))
								.collect(Collectors.toList()));
		
		return userProfilesDto;
	}

	@Override
	public List<UserProfileDto> findAll(String keyword) {
		List<UserProfile> userProfiles = repository.search(keyword);
		
		List<UserProfileDto> dtos = userProfiles.stream()
									.map(t -> UserProfileMapper.INSTANCE.toUserProfileDto(t))
									.collect(Collectors.toList());
		
		return dtos;
	}

	@Override
	public UserProfileDetailsDto findById(long id) {
		
		UserProfile userProfile = repository.findById(id)
				.orElseThrow(() -> new UserProfileNotFoundException("Profile not found."));
		
		UserProfileDetailsDto dto = UserProfileMapper.INSTANCE
										.toUserProfileDetailsDto(userProfile);
		
		return dto;
	}

	@Override
	public UserProfileDto getOwnProfile() {
		
		UserProfile userProfile = authService.getCurrentUserProfile();
		
		UserProfileDto dto = UserProfileMapper.INSTANCE
										.toUserProfileDto(userProfile);
		
		return dto;
	}

	@Override
	public void update(long id, UserProfileDto dto) {
		UserProfile userProfile = repository.findById(id)
				.orElseThrow(() -> new UserProfileNotFoundException("Profile not found."));
		
		userProfile.setGivenName(dto.getGivenName());
		userProfile.setSurname(dto.getSurname());
		userProfile.setDateOfBirth(dto.getDateOfBirth());
		
		repository.save(userProfile);
	}
}
