package com.example.authenticatingldap.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="person_qualification")
public class PersonQualification {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "person_id")
	private Person person;
	
	@ManyToOne
    @JoinColumn(name = "qualification_id")
	private Qualification qualification;
	
	@Column(name="completed")
	private LocalDate completed;
	
	@Column(name="grade")
	private String grade;

	public Long getId() {
		return id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public LocalDate getCompleted() {
		return completed;
	}

	public void setCompleted(LocalDate completed) {
		this.completed = completed;
	}

}
