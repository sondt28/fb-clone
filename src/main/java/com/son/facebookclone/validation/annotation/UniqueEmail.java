package com.son.facebookclone.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.son.facebookclone.validation.validator.UniqueEmailValidator;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueEmail {
	String message() default "Email already existed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
