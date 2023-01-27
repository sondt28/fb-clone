package com.son.facebookclone.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDto {

	private long id;
	
	@NotBlank(message = "given name must not be blank")
	private String givenName;
	
	@NotBlank(message = "surname must not be blank")
	private String surname;
	
	private LocalDate dateOfBirth;
	private String email;
}
