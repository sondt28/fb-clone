package com.son.facebookclone.dto;

import com.son.facebookclone.validation.annotation.ExistedEmail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {

	@ExistedEmail
	private String email;
	
	private String password;
}
