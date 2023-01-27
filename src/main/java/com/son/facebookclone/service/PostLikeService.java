package com.son.facebookclone.service;

public interface PostLikeService {
	
	void save(long id);
	void deleteByUserProfileAndUserPost(Long id);
}
