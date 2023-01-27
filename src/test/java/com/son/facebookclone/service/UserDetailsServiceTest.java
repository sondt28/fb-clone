package com.son.facebookclone.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.son.facebookclone.repository.UserProfileRepository;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

	@Mock
	private UserProfileRepository repository;
	
	@InjectMocks
	private UserDetailsServiceImpl service;
	
}
