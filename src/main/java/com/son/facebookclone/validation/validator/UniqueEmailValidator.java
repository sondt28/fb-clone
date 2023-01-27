package com.son.facebookclone.validation.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.son.facebookclone.model.UserProfile;
import com.son.facebookclone.repository.UserProfileRepository;
import com.son.facebookclone.validation.annotation.UniqueEmail;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	private UserProfileRepository repository;
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		
		Optional<UserProfile> userProfileOpt = repository.findByEmail(email);
		
		if (userProfileOpt.isEmpty())
			return true;
			
		return false;
	}
}
