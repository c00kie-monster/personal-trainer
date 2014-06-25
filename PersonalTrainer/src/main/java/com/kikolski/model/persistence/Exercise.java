
package com.kikolski.model.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.kikolski.model.validation.interfaces.NotVulgarism;

@Entity
public class Exercise implements Serializable{
	private static final long serialVersionUID = 9134143186434079669L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column (unique = true, nullable = false, length=16)
	@NotVulgarism(message = "Brzydkie słówko w polu \"Nazwa ćwiczenia\"")
	@Size(min = 1, max = 16, message="Nazwa ćwiczenia musi mieć od 1 do 16 znaków")
	private String name;
	
	@Column (nullable = false, length=16)
	@NotVulgarism(message = "Brzydkie słówko w polu \"Nazwa ćwiczenia\"")
	@Size(min = 4, max = 16, message="Nazwa partii ciała musi mieć od 1 do 16 znaków")
	private String bodyPart;
	
	private String description;
		
	public Exercise() {
		
	}
	
	public Exercise(String name, String bodyPart) {
		this(name, bodyPart, "");
	}
	
	public Exercise(String name, String bodyPart, String description){
		this.name = name;
		this.bodyPart = bodyPart;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
	public String getBodyPart() {
		return bodyPart;
	}

	public void setBodyPart(String bodyPart) {
		this.bodyPart = bodyPart;
	}

	@Override
	public String toString() {
		return this.name;
	}
}