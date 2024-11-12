package com.example.authenticatingldap.model;

import java.util.HashSet;
import java.util.Set;

public class PersonData {
	
	private Long id;
	private String firstName;
	private String surName;
	private String gender;
	private boolean completed;
	private Set<PersonQualificationData> personQualifications = new HashSet<>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public Set<PersonQualificationData> getPersonQualifications() {
		return personQualifications;
	}
	public void setPersonQualifications(Set<PersonQualificationData> personQualifications) {
		this.personQualifications = personQualifications;
	}
	

}
