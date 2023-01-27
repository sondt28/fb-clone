package com.son.facebookclone.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.son.facebookclone.dto.LoginDto;
import com.son.facebookclone.dto.RegisterDto;
import com.son.facebookclone.repository.UserProfileRepository;
import com.son.facebookclone.service.AuthService;
import com.son.facebookclone.utils.JwtHelper;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private AuthService service;
	
	@MockBean
	private PasswordEncoder encoder;
	
	@MockBean
	private JwtHelper helper;
	
	@MockBean
	private UserProfileRepository repository;
	
	private RegisterDto registerDto;
	
	private LoginDto loginDto;
	
	@BeforeEach
	void beforeEach() {
		registerDto = new RegisterDto();
		registerDto.setGivenName("Son");
		registerDto.setSurname("Le");
		registerDto.setEmail("son1");
		registerDto.setDateOfBirth(LocalDate.of(2002, 06, 28));
		registerDto.setPassword("123456789");
		
		loginDto = new LoginDto();
		loginDto.setEmail("son1");
		loginDto.setPassword("99999");
	}
	
	@Test
	@WithMockUser
	void shouldSaveEntityAndReturn201() throws Exception {
		
		when(service.register(any(RegisterDto.class))).thenReturn(registerDto);
		
		RequestBuilder request = MockMvcRequestBuilders
								.post("/api/v1/auth/register")
								.with(csrf())
								.contentType(MediaType.APPLICATION_JSON)
								.content(mapper.writeValueAsString(registerDto));
		
		mockMvc.perform(request)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.surname", is("Le")));
	}
	
	@Test
	void shouldReturnJwtTokenAnd200() {
		when(service.login(any(LoginDto.class))).thenReturn(null);
	}
}
