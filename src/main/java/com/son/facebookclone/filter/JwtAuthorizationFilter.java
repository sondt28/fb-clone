package com.son.facebookclone.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.son.facebookclone.utils.JwtHelper;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtHelper helper;
	
	@Autowired
	private UserDetailsService service;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = helper.getToken(request);
		
		if (helper.validateToken(token)) {
			String email = helper.getUsername(token);
			UserDetails userDetails = service.loadUserByUsername(email);
			Authentication auth = 
					new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());
		
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		filterChain.doFilter(request, response);
	}

}
