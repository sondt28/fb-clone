package com.son.facebookclone.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.son.facebookclone.dto.RegisterDto;
import com.son.facebookclone.model.UserProfile;
import com.son.facebookclone.repository.UserProfileRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	@Mock
	private UserProfileRepository repository;
	
	@Mock
	private PasswordEncoder encoder;
	
	@InjectMocks
	private AuthServiceImpl service;
	
	private RegisterDto registerDto;
	
	private UserProfile userProfile;
	
	private String encode;
	@BeforeEach
	void beforeEach() {
		encode = encoder.encode("1");
		
		registerDto = new RegisterDto();
		registerDto.setGivenName("Son");
		registerDto.setSurname("Le");
		registerDto.setEmail("son123");
		registerDto.setDateOfBirth(LocalDate.of(2032, 06, 28));
		registerDto.setPassword("");
		
		userProfile = new UserProfile();
		userProfile.setGivenName("Son");
		userProfile.setSurname("Le");
		userProfile.setEmail("son123");
		userProfile.setDateOfBirth(LocalDate.of(2032, 06, 28));
		userProfile.setPassword(encode);	
	}
	
	@Test
	void shouldReturnRegisterDto() {
		when(repository.save(any(UserProfile.class))).thenReturn(userProfile);
		
		RegisterDto actual = service.register(registerDto);
		
		assertEquals("Le", actual.getSurname());
		assertEquals("Son", actual.getGivenName());
		assertEquals("son123", actual.getEmail());
		assertEquals(LocalDate.of(2032, 06, 28), actual.getDateOfBirth());
		assertEquals("", actual.getPassword());
	}
}
