package com.son.facebookclone.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.son.facebookclone.dto.LoginDto;
import com.son.facebookclone.dto.RegisterDto;
import com.son.facebookclone.mapper.UserProfileMapper;
import com.son.facebookclone.model.UserProfile;
import com.son.facebookclone.repository.UserProfileRepository;
import com.son.facebookclone.utils.JwtHelper;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserProfileRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JwtHelper jwt;
	
	@Override
	public String login(LoginDto dto) {
		Optional<UserProfile> userProfileOptional = repository.findByEmail(dto.getEmail());
		
		String passwordStorage = userProfileOptional.get().getPassword();
		
		boolean checkPassword = encoder.matches(dto.getPassword(), passwordStorage);
		
		if (checkPassword) {
			return jwt.generateToken(dto.getEmail());
		}
		
		return null;
	}

	@Override
	public RegisterDto register(RegisterDto dto) {
		String encodePassword = encoder.encode(dto.getPassword());
		dto.setPassword(encodePassword);
		
		UserProfile userProfile = UserProfileMapper.INSTANCE.toModel(dto);
		
		userProfile = repository.save(userProfile);
		
		userProfile.setPassword("");
		
		RegisterDto newDto = UserProfileMapper.INSTANCE.toDto(userProfile);
		
		return newDto;
	}

	@Override
	public UserProfile getCurrentUserProfile() {
		String principal = (String) SecurityContextHolder
						.getContext()
						.getAuthentication()
						.getPrincipal();
		
		Optional<UserProfile> userProfileOpt = repository.findByEmail(principal);

		return userProfileOpt.get();
	}
}
