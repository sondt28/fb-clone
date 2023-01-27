package com.son.facebookclone.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.son.facebookclone.utils.JwtHelper;


@WebMvcTest(WelcomeController.class)
public class WelcomeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private JwtHelper helper;
	
	@Test
	@WithMockUser(value = "son")
	void shouldReturn200() throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders
								.get("/welcome")
								.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(request)
								.andExpect(status().isOk())
								.andReturn();
		
		assertEquals("Welcome to Facebook clone application !", result.getResponse().getContentAsString());
	}
}
