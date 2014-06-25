package com.kikolski.model.validation.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.kikolski.model.validation.interfaces.NotVulgarism;

public class VulgarismConstraintValidator implements ConstraintValidator<NotVulgarism, String>{
	private final String[] vulgarisms = {"dupa"};
	
	@Override
	public void initialize(NotVulgarism word) {}

	@Override
	public boolean isValid(String word, ConstraintValidatorContext arg1) {
		if (word == null || word.trim().length() == 0 )
			return true;
					
		return !Arrays.asList(vulgarisms).contains(word.toLowerCase());
	}
}