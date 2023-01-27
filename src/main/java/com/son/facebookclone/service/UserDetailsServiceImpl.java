package com.son.facebookclone.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.son.facebookclone.model.UserProfile;
import com.son.facebookclone.repository.UserProfileRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserProfileRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<UserProfile> userProfileOpt = repository.findByEmail(email);
		
		UserProfile userProfile = userProfileOpt.get();
		
		return new User(userProfile.getEmail(), userProfile.getPassword(), new ArrayList<>());
	}
}
