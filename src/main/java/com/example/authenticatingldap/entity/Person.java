package com.example.authenticatingldap.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="person")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Person {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false)
	private Long id;
	
	@Column(name="firstname", nullable=false)
	private String firstName;
	
	@Column(name="surname", nullable=false)
	private String surName;
	
	@Column(name="gender", nullable=false)
	private String gender;
	
	@Column(name="completed")
	private boolean completed;
	
	@JsonIgnore
	@OneToMany(mappedBy = "person")
	private Set<PersonQualification> personQualifications = new HashSet<>();

	public Long getId() {
		return id;
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
	
	public Set<PersonQualification> getPersonQualifications() {
		return personQualifications;
	}

	public void setPersonQualifications(Set<PersonQualification> personQualifications) {
		this.personQualifications = personQualifications;
	}
	

}
