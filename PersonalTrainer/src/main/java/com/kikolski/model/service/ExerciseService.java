package com.kikolski.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.kikolski.model.dao.GenericDAO;
import com.kikolski.model.exception.DAOException;
import com.kikolski.model.exception.ValidationException;
import com.kikolski.model.persistence.Exercise;

public class ExerciseService {
	public static final String BEAN_ID = "exerciseService"; 

	private GenericDAO<Exercise> exerciseDAO;
	
	public void add(Exercise exercise) throws ValidationException, DAOException{
		exerciseDAO.add(exercise);
	}
	
	public List<Exercise> getAll() {
		List<Exercise> result = exerciseDAO.getAll();
		if (result == null)
			return new ArrayList<Exercise>();
		return result;
	}

	public void delete(Exercise exercise) throws DAOException{
		if (exercise != null)
				exerciseDAO.delete(exercise);
	}
	
	public void update(Exercise exercise)  throws ValidationException, DAOException{
			exerciseDAO.update(exercise);
	}
	
	public void setExerciseDAO(GenericDAO<Exercise> exerciseDAO) {
		this.exerciseDAO = exerciseDAO;
	}
}