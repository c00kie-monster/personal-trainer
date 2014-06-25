package com.kikolski.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.exception.DataException;

import com.kikolski.model.exception.DAOException;
import com.kikolski.model.persistence.Exercise;
import com.kikolski.model.persistence.Workout;

public class WorkoutDAO extends AbstractDAO implements GenericDAO<Workout>{

	private Session currentSession;
	
	@Override
	public void add(Workout workout) throws DAOException {
		try {
			currentSession = sessionFactory.getCurrentSession();
			currentSession.beginTransaction();
			currentSession.save(workout);
			currentSession.getTransaction().commit(); 
		} catch (DataException dataException) {
			currentSession.getTransaction().rollback();
			throw new DAOException();
		}		
	}

	@Override
	public List<Workout> getAll() {
			currentSession = sessionFactory.getCurrentSession();
			currentSession.beginTransaction();
			Criteria criteria = currentSession.createCriteria(Workout.class);
			List<Workout> workouts = (List<Workout>) criteria.list();
			for (Workout w : workouts)
					Hibernate.initialize(w.getExercises());
			currentSession.getTransaction().commit(); 
			return workouts;
	}

	@Override
	public void delete(Workout workout) throws DAOException {
		try {
			currentSession = sessionFactory.getCurrentSession();
		
		currentSession.beginTransaction();
		currentSession.delete(workout);
		currentSession.getTransaction().commit();
		} catch (DataException exception) {
			currentSession.getTransaction().rollback();
			throw new DAOException();
		}
	}

	@Override
	public void update(Workout workout) {
		// TODO Auto-generated method stub
		
	}

}
