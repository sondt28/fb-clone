package com.son.facebookclone.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.son.facebookclone.dto.PostResponse;
import com.son.facebookclone.service.UserPostService;
import com.son.facebookclone.utils.JwtHelper;

@WebMvcTest(UserPostController.class)
public class UserPostControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserPostService service;
	
	@MockBean
	private JwtHelper jwtHelper;
	
	private LocalDateTime dateTime = LocalDateTime.now();
	
	private PostResponse postResponse;
	
	private PostResponse postResponse2;
	@BeforeEach
	void beforeEach() {
		postResponse = new PostResponse();
		postResponse.setId(1);
		postResponse.setGivenName("Son");
		postResponse.setSurname("Le");
		postResponse.setCreatedDateTime(dateTime);
		postResponse.setTotalComment(0);
		postResponse.setTotalLike(0);
		postResponse.setWrittenText("abc");
		
		postResponse2 = new PostResponse();
		postResponse2.setId(2);
		postResponse2.setGivenName("Son");
		postResponse2.setSurname("Le");
		postResponse2.setCreatedDateTime(dateTime);
		postResponse2.setTotalComment(5);
		postResponse2.setTotalLike(65);
		postResponse2.setWrittenText("abc");
	}
}
