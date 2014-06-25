package com.kikolski.model.exception;

import java.util.ArrayList;
import java.util.List;

import com.kikolski.model.validation.error.UserError;

public class ValidationException extends Exception {
	private static final long serialVersionUID = 386851483717867326L;
	private List<UserError> errorsList;
	
	public ValidationException(List<UserError> errorsList) {
		super();
		if (errorsList == null)
			this.errorsList = new ArrayList<UserError>();
		else
			this.errorsList = errorsList;
	}
	
	public List<UserError> getErrorsList(){
		return this.errorsList;
	}
}
