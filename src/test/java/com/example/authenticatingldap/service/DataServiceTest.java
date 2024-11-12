package com.example.authenticatingldap.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.authenticatingldap.entity.Person;
import com.example.authenticatingldap.entity.PersonQualification;
import com.example.authenticatingldap.entity.Qualification;
import com.example.authenticatingldap.mapper.PersonMapper;
import com.example.authenticatingldap.model.PersonData;
import com.example.authenticatingldap.model.PersonQualificationData;
import com.example.authenticatingldap.model.QualificationData;
import com.example.authenticatingldap.repository.PersonQualificationRepository;
import com.example.authenticatingldap.repository.PersonRepository;
import com.example.authenticatingldap.repository.QualificationRepository;

@SpringBootTest
public class DataServiceTest {
	
	@Autowired
	private DataService dataService;
	
	@MockBean
	private PersonRepository personRepository;
	
	@MockBean
	private PersonMapper personMapper;
	
	@MockBean
	private QualificationRepository qualificationRepository;
	
	@MockBean
	private PersonQualificationRepository personQualificationRepository;
	
	@MockBean
	private Person mockPerson;
	
	@MockBean
	private Qualification mockQualification;
	
	@Test
	public void testGetPersonById_PersonFound() throws Exception {
		Person testPerson = new Person();
		when(personRepository.findById(1000l)).thenReturn(Optional.of(testPerson));
		PersonData mockPersonData = new PersonData();
		mockPersonData.setId(1000l);
		when(personMapper.toModel(testPerson)).thenReturn(mockPersonData);
		PersonData personById = dataService.getPersonById(1000l);
		assertNotNull(personById);
		assertEquals(1000l, personById.getId());
		
	}
	
	@Test
	public void testGetPersonById_PersonNotFound() throws Exception {
		when(personRepository.findById(1000l)).thenReturn(Optional.empty());
		PersonData personById = dataService.getPersonById(1000l);
		assertNull(personById);
	}
	
	@Test
	public void testPersonQualificationAlreadyExist_PersonNotFound_ExceptionThrown() {
		when(personRepository.findById(1000l)).thenReturn(Optional.empty());
		assertThrows(IllegalStateException.class, () -> dataService.isPersonQualificationAlreadyExist(1000l, new QualificationData()), "Person does not exist for person id : 1000l");
	}
	
	@Test
	public void testPersonQualificationAlreadyExist_False_PersonFound_QualificationFound_PersonQualificationDoesNotExist() throws Exception {
		Person testPerson = new Person();
		when(personRepository.findById(1000l)).thenReturn(Optional.of(testPerson));
		Qualification testQualification = new Qualification();
		when(qualificationRepository.findById(1001l)).thenReturn(Optional.of(testQualification));
		when(personQualificationRepository.findByPersonAndQualification(testPerson, testQualification)).thenReturn(Optional.empty());
		QualificationData qualificationData = new QualificationData();
		qualificationData.setId(1001l);
		assertFalse(dataService.isPersonQualificationAlreadyExist(1000l, qualificationData));
	}
	
	@Test
	public void testPersonQualificationAlreadyExist_True_PersonFound_QualificationFound_PersonQualificationDoesNotExist() throws Exception {
		Person testPerson = new Person();
		when(personRepository.findById(1000l)).thenReturn(Optional.of(testPerson));
		Qualification testQualification = new Qualification();
		when(qualificationRepository.findById(1001l)).thenReturn(Optional.of(testQualification));
		PersonQualification mockPersonQualification = new PersonQualification();
		when(personQualificationRepository.findByPersonAndQualification(testPerson, testQualification)).thenReturn(Optional.of(mockPersonQualification));
		QualificationData qualificationData = new QualificationData();
		qualificationData.setId(1001l);
		assertTrue(dataService.isPersonQualificationAlreadyExist(1000l, qualificationData));
	}
	
	@Test
	public void testUpdatePersonQualification() throws Exception {
		when(mockPerson.getId()).thenReturn(1000l);
		when(mockPerson.isCompleted()).thenReturn(true);
		when(personRepository.findById(1000l)).thenReturn(Optional.of(mockPerson));
		when(mockQualification.getId()).thenReturn(1001l);
		when(qualificationRepository.findById(1001l)).thenReturn(Optional.of(mockQualification));
		QualificationData qualificationData = new QualificationData();
		qualificationData.setId(1001l);
		PersonQualificationData personQualificationData = dataService.updatePersonQualification(1000l, qualificationData);
		assertTrue(mockPerson.isCompleted());
		assertNotNull(personQualificationData);
		assertEquals(1000l, personQualificationData.getPersonId());
		assertEquals(1001l, personQualificationData.getQualificationId());
		assertEquals("PASS", personQualificationData.getGrade());
	}
	
	

}
