package com.son.facebookclone.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.son.facebookclone.validation.validator.ExistedEmailValidator;

@Constraint(validatedBy = ExistedEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExistedEmail {

	String message() default "Email not found.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
