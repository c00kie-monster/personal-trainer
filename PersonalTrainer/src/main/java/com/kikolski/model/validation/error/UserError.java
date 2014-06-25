package com.kikolski.model.validation.error;

public class UserError {
	private String fieldName;
	private String message;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
		
	@Override
	public String toString() {
		return fieldName + " " + message;
	}
}
