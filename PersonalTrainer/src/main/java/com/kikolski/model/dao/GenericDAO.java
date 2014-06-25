package com.kikolski.model.dao;

import java.util.List;

import com.kikolski.model.exception.DAOException;

public interface GenericDAO<T> {
	void add(T object) throws DAOException;
	void delete(T object) throws DAOException;
	void update(T object) throws DAOException;
	List<T> getAll();
}
