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
@Table(name="qualification")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Qualification {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false)
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="description")
	private String description;
	
	@OneToMany(mappedBy = "qualification")
	@JsonIgnore
	private Set<PersonQualification> personQualifications = new HashSet<>();


	public Long getId() {
		return id;
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
	
	public Set<PersonQualification> getPersonQualifications() {
		return personQualifications;
	}

	public void setPersonQualifications(Set<PersonQualification> personQualifications) {
		this.personQualifications = personQualifications;
	}

	

}
