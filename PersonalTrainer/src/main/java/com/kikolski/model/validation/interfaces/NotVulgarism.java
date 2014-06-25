package com.kikolski.model.validation.interfaces;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.kikolski.model.validation.validator.VulgarismConstraintValidator;

@Documented
@Constraint(validatedBy = VulgarismConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotVulgarism {
	String message() default "";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {}; 
}
