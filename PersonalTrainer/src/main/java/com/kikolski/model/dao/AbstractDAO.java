package com.kikolski.model.dao;

import org.hibernate.SessionFactory;

public abstract class AbstractDAO{
	protected SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
