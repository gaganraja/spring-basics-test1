package com.example.authenticatingldap.model;

import java.time.LocalDate;

public class PersonQualificationData {
	
	private Long id;
	private Long personId;
	private Long qualificationId;

	private LocalDate completed;
	private String grade;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDate getCompleted() {
		return completed;
	}
	public void setCompleted(LocalDate completed) {
		this.completed = completed;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getQualificationId() {
		return qualificationId;
	}
	public void setQualificationId(Long qualificationId) {
		this.qualificationId = qualificationId;
	}

}
