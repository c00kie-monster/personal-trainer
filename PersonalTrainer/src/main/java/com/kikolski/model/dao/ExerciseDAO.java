package com.kikolski.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.exception.DataException;
import org.postgresql.jdbc2.EscapedFunctions;
import org.postgresql.util.PSQLException;

import com.kikolski.model.exception.DAOException;
import com.kikolski.model.persistence.Exercise;

public class ExerciseDAO extends AbstractDAO implements GenericDAO<Exercise> {

	private Session currentSession;

	@Override
	public List<Exercise> getAll() {
		currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();
		Criteria criteria = currentSession.createCriteria(Exercise.class);
		List<Exercise> exercises = (List<Exercise>) criteria.list();
		currentSession.getTransaction().commit();
		return exercises;
	}

	@Override
	public void add(Exercise exercise) throws DAOException {
		try {
			currentSession = sessionFactory.getCurrentSession();
			currentSession.beginTransaction();
			currentSession.save(exercise);
			currentSession.getTransaction().commit();
		} catch (Exception dataException) {
			currentSession.getTransaction().rollback();
			throw new DAOException(dataException.getMessage());
		}
	}

	public void delete(Exercise exercise) throws DAOException {
		try {
			currentSession = sessionFactory.getCurrentSession();
			currentSession.beginTransaction();
			currentSession.delete(exercise);
			currentSession.getTransaction().commit();
		} catch (Exception eception) {
			currentSession.getTransaction().rollback();
			throw new DAOException();
		}
	}

	public void update(Exercise exercise) throws DAOException {
		try {
			currentSession = sessionFactory.getCurrentSession();
			currentSession.beginTransaction();
			currentSession.update(exercise);
			currentSession.getTransaction().commit();
		} catch (Exception exception) {
			currentSession.getTransaction().rollback();
			throw new DAOException();
		}
	}
}
