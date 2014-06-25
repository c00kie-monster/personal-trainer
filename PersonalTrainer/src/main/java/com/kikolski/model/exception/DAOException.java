package com.kikolski.model.exception;

public class DAOException extends Exception{
	private static final long serialVersionUID = -722982442610032356L;
	
	public DAOException() {
		super();
	}
	
	public DAOException(String message) {
		super(message);
	}
}
