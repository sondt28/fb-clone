package com.son.facebookclone.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.son.facebookclone.validation.annotation.UniqueEmail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDto {
	
	@NotBlank(message = "{userProfile.surname.notBlank}")
	private String surname;
	
	@NotBlank(message = "{userProfile.givenName.notBlank}")
	private String givenName;
	
	@NotNull(message = "{userProfile.dateOfBirth.notNull}")
	private LocalDate dateOfBirth;
	
	@UniqueEmail
	private String email;
	
	@NotBlank(message = "{userProfile.password.notBlank}")
	@Size(min = 8, max = 15, message = "{userProfile.password.size}")
	private String password;
}
