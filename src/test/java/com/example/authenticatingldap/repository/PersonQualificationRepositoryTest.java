package com.example.authenticatingldap.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.authenticatingldap.entity.Person;
import com.example.authenticatingldap.entity.PersonQualification;
import com.example.authenticatingldap.entity.Qualification;

@DataJpaTest
public class PersonQualificationRepositoryTest {
	
	@Autowired
	private PersonQualificationRepository personQualificationRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private QualificationRepository qualificationRepository;
	
	@Test
	public void testFindByPersonAndQualification() {
		
		Person person = new Person();
		person.setFirstName("firstName");
		person.setGender("M");
		person.setSurName("surName");
		
		personRepository.save(person);
		
		Qualification qualification = new Qualification();
		qualification.setName("Qual1");
		qualification.setDescription("Descr");
		
		qualificationRepository.save(qualification);
		
		
		PersonQualification personQualification = new PersonQualification();
		personQualification.setPerson(person);
		personQualification.setQualification(qualification);
		personQualification.setCompleted(LocalDate.now());
		personQualification.setGrade("PASS");
		personQualificationRepository.save(personQualification);
		
		Optional<PersonQualification> byPersonAndQualification = personQualificationRepository.findByPersonAndQualification(person, qualification);
		
		assertTrue(byPersonAndQualification.isPresent());
		assertEquals(person.getId(), byPersonAndQualification.get().getPerson().getId());
		assertEquals(qualification.getId(), byPersonAndQualification.get().getQualification().getId());
		
		
	}

}
