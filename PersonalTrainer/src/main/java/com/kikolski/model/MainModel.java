package com.kikolski.model;

import java.util.ArrayList;
import java.util.List;

import com.kikolski.model.persistence.Exercise;
import com.kikolski.model.persistence.Workout;

public class MainModel {
	private List<Exercise> exercises;
	private List<Workout> workouts;

	
	public MainModel() {
		exercises = new ArrayList<>();
		workouts = new ArrayList<>();
	}
	
	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}
	
	public List<Workout> getWorkouts() {
		return workouts;
	}
	
	public void setWorkouts(List<Workout> workouts) {
		this.workouts = workouts;
	}
}