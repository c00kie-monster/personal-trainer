package com.kikolski.model.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validator;

import com.kikolski.model.dao.WorkoutDAO;
import com.kikolski.model.exception.DAOException;
import com.kikolski.model.exception.ValidationException;
import com.kikolski.model.persistence.Workout;

public class WorkoutService {
	private WorkoutDAO workoutDAO;
	
	public void add(Workout workout) throws ValidationException, DAOException{
			workoutDAO.add(workout);
	}

	public void delete(Workout workout) {
		if (workout != null)
			try {
				workoutDAO.delete(workout);
			} catch (DAOException e) {
				e.printStackTrace();
			}
	}
	
	public List<Workout> getAll() {
		List<Workout> result = workoutDAO.getAll();
		if (result == null)
			return new ArrayList<Workout>();
		return result;
	}
	
	public void setWorkoutDAO(WorkoutDAO workoutDAO) {
		this.workoutDAO = workoutDAO;
	}
}