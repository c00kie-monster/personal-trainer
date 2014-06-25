package com.kikolski;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.kikolski.model.persistence.Exercise;

public class ExerciseValidatorTest {
	private final static int NO_ERRORS = 0;
	private Validator validator;
	
	@BeforeSuite
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
	
	@Test
	public void shouldReturnTrueWhenExerciseIsCorrectlyFilled() {
		Exercise exercise = new Exercise();
		exercise.setId(1);
		exercise.setName("Squat");
		exercise.setBodyPart("Legs");
		exercise.setDescription("No Pain No Gain");
		Set<ConstraintViolation<Exercise>> constraintViolations = validator.validate(exercise);
		Assert.assertTrue(constraintViolations.size() == NO_ERRORS);
	}
	
	@Test
	public void shouldReturnFalseWhenBodyPartIsEmpty() {
		Exercise exercise = new Exercise();
		exercise.setId(1);
		exercise.setName("Squat");
		exercise.setBodyPart("");
		exercise.setDescription("No Pain No Gain");
		Set<ConstraintViolation<Exercise>> constraintViolations = validator.validate(exercise);
		Assert.assertFalse(constraintViolations.size() == NO_ERRORS);
	}
	
	@Test
	public void shouldReturnFalseWhenNameIsEmpty() {
		Exercise exercise = new Exercise();
		exercise.setId(1);
		exercise.setName("");
		exercise.setBodyPart("Legs");
		exercise.setDescription("No Pain No Gain");
		Set<ConstraintViolation<Exercise>> constraintViolations = validator.validate(exercise);
		Assert.assertFalse(constraintViolations.size() == NO_ERRORS);
	}
	
	@Test
	public void shouldReturnTrueWhenDescriptionIsEmpty() {
		Exercise exercise = new Exercise();
		exercise.setId(1);
		exercise.setName("Squat");
		exercise.setBodyPart("Legs");
		Set<ConstraintViolation<Exercise>> constraintViolations = validator.validate(exercise);
		Assert.assertTrue(constraintViolations.size() == NO_ERRORS);
	}
	
	@Test
	public void shouldReturnFalseWhenBodyPartIsVulgarism() {
		Exercise exercise = new Exercise();
		exercise.setId(1);
		exercise.setName("Squat");
		exercise.setBodyPart("Dupa");
		Set<ConstraintViolation<Exercise>> constraintViolations = validator.validate(exercise);
		Assert.assertFalse(constraintViolations.size() == NO_ERRORS);
	}
}
