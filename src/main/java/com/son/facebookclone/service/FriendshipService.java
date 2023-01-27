package com.son.facebookclone.service;

public interface FriendshipService {

	void saveFriendship(Long userProfileId);
	void removeFriendship(Long userProfileId);
}
