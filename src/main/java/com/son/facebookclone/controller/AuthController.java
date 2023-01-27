package com.son.facebookclone.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.son.facebookclone.dto.LoginDto;
import com.son.facebookclone.dto.RegisterDto;
import com.son.facebookclone.service.AuthService;
import com.son.facebookclone.utils.ErrorHelper;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthService service;
	
	@PostMapping("login")
	public Object login(@Valid @RequestBody LoginDto dto,
						BindingResult errors) {
		if (errors.hasErrors())
			return new ResponseEntity<>(ErrorHelper.getErrorMessage(errors), 
										HttpStatus.BAD_REQUEST);	
		String token = service.login(dto);
		if (token == null)
			return new ResponseEntity<>("Password is not correct.", 
										HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(token, HttpStatus.CREATED);
	}
	
	@PostMapping("register")
	public Object register(@Valid @RequestBody RegisterDto dto,
						   BindingResult errors) {
		if (errors.hasErrors())
			return new ResponseEntity<>(ErrorHelper.getErrorMessage(errors), 
										HttpStatus.BAD_REQUEST);
		RegisterDto newDto = service.register(dto);
		
		return new ResponseEntity<>(newDto, HttpStatus.CREATED);
	}
}
