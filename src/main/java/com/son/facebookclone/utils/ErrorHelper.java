package com.son.facebookclone.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

public class ErrorHelper {

	public static List<String> getErrorMessage(BindingResult errors) {
		
		return errors.getAllErrors()
				.stream()
				.map(t -> t.getDefaultMessage())
				.collect(Collectors.toList());
	}
}
