package com.son.facebookclone.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.son.facebookclone.exceptions.FriendshipException;
import com.son.facebookclone.exceptions.UserProfileNotFoundException;
import com.son.facebookclone.model.Friendship;
import com.son.facebookclone.model.FriendshipPK;
import com.son.facebookclone.model.UserProfile;
import com.son.facebookclone.repository.FriendshipRepository;
import com.son.facebookclone.repository.UserProfileRepository;

@Service
public class FriendshipServiceImpl implements FriendshipService {

	@Autowired
	private FriendshipRepository repository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public void saveFriendship(Long userProfileId) {
		
		UserProfile profileRequest = authService.getCurrentUserProfile();
	
		if (userProfileId == profileRequest.getId())
			throw new FriendshipException("Illegal profile!");
		
		
		UserProfile profileAccept = userProfileRepository.findById(userProfileId)
				.orElseThrow(() -> new UserProfileNotFoundException("Profile not found."));
		
		FriendshipPK friendshipPK = new FriendshipPK();
		friendshipPK.setProfileRequest(profileAccept);
		friendshipPK.setProfileAccept(profileRequest);
		
		Optional<Friendship> friendshipOpt = repository.findById(friendshipPK);
		
		if (friendshipOpt.isPresent())
			throw new FriendshipException("You have been following this profile.");
		
		friendshipPK.setProfileRequest(profileRequest);
		friendshipPK.setProfileAccept(profileAccept);
		
		friendshipOpt = repository.findById(friendshipPK);
		
		if (friendshipOpt.isPresent())
			throw new FriendshipException("You have been following this profile.");
		
		Friendship friendship = new Friendship();
		friendshipPK.setProfileRequest(profileRequest);
		friendshipPK.setProfileAccept(profileAccept);
		friendship.setId(friendshipPK);
		
		repository.save(friendship);
	}

	@Override
	public void removeFriendship(Long userProfileId) {
		UserProfile profileRequest = authService.getCurrentUserProfile();
		
		if (userProfileId == profileRequest.getId())
			throw new FriendshipException("Illegal profile!");
		
		UserProfile profileUnfriend = userProfileRepository.findById(userProfileId)
				.orElseThrow(() -> 
					new UserProfileNotFoundException("You haven't followed this profile yet."));

		FriendshipPK friendshipPK = new FriendshipPK();
		friendshipPK.setProfileRequest(profileRequest);
		friendshipPK.setProfileAccept(profileUnfriend);
		
		Optional<Friendship> friendshipOpt = repository.findById(friendshipPK);
		
		if (friendshipOpt.isPresent()) {		
			repository.delete(friendshipOpt.get());
		} else {
			friendshipPK.setProfileRequest(profileUnfriend);
			friendshipPK.setProfileAccept(profileRequest);

			friendshipOpt = repository.findById(friendshipPK);
			
			if (friendshipOpt.isPresent()) {
				repository.delete(friendshipOpt.get());
			} else {
				throw new FriendshipException("You haven't followed this profile yet.");
			}
		}
	}
}
