package com.son.facebookclone.service;

import com.son.facebookclone.dto.LoginDto;
import com.son.facebookclone.dto.RegisterDto;
import com.son.facebookclone.model.UserProfile;

public interface AuthService {

	String login(LoginDto dto);
	RegisterDto register(RegisterDto dto);
	UserProfile getCurrentUserProfile();
}
