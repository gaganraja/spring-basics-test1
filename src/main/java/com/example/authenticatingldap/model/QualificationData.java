package com.example.authenticatingldap.model;

import java.util.HashSet;
import java.util.Set;

public class QualificationData {
	
	private Long id;
	private String name;
	private String description;
	private Set<PersonQualificationData> personQualifications = new HashSet<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Set<PersonQualificationData> getPersonQualifications() {
		return personQualifications;
	}
	public void setPersonQualifications(Set<PersonQualificationData> personQualifications) {
		this.personQualifications = personQualifications;
	}

}
