package com.kikolski.aop;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.kikolski.model.exception.ValidationException;
import com.kikolski.model.persistence.Exercise;
import com.kikolski.model.persistence.Workout;
import com.kikolski.model.validation.error.UserError;

public class ValidatingAspect {
	private Validator validator;
	
	public void validateExercise(Object object) throws ValidationException {
			Exercise ex = (Exercise) object;
			Set<ConstraintViolation<Exercise>> out = validator.validate(ex);
			if (out.size() != 0)
				throw new ValidationException(extractExerciseErrors(out));
	}
	
	public void validateWorkout(Object object) throws ValidationException {
			Workout w = (Workout) object;
			Set<ConstraintViolation<Workout>> out = validator.validate(w);
			if (out.size() != 0)
				throw new ValidationException(extractWorkoutErrors(out));
	}
	
	private List<UserError> extractExerciseErrors(Set<ConstraintViolation<Exercise>> constraints) {
		List<UserError> errors = new ArrayList<>();
			for (ConstraintViolation<Exercise> e : constraints) {
				UserError error = new UserError();
				error.setMessage(e.getMessage());
				error.setFieldName(e.getPropertyPath().toString());
				errors.add(error);
			}
		return errors;
	}
	
	private List<UserError> extractWorkoutErrors(Set<ConstraintViolation<Workout>> constraints) {
		List<UserError> errors = new ArrayList<>();
			for (ConstraintViolation<Workout> e : constraints) {
				UserError error = new UserError();
				error.setMessage(e.getMessage());
				error.setFieldName(e.getPropertyPath().toString());
				errors.add(error);
			}
		return errors;
	}
	
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}
