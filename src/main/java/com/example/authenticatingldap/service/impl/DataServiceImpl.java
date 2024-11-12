package com.example.authenticatingldap.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.authenticatingldap.entity.Person;
import com.example.authenticatingldap.entity.PersonQualification;
import com.example.authenticatingldap.entity.Qualification;
import com.example.authenticatingldap.mapper.PersonMapper;
import com.example.authenticatingldap.mapper.PersonQualificationMapper;
import com.example.authenticatingldap.model.PersonData;
import com.example.authenticatingldap.model.PersonQualificationData;
import com.example.authenticatingldap.model.QualificationData;
import com.example.authenticatingldap.repository.PersonQualificationRepository;
import com.example.authenticatingldap.repository.PersonRepository;
import com.example.authenticatingldap.repository.QualificationRepository;
import com.example.authenticatingldap.service.DataService;

@Service
public class DataServiceImpl implements DataService{
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private QualificationRepository qualificationRepository;
	
	@Autowired
	private PersonQualificationRepository personQualificationRepository;
	
	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private PersonQualificationMapper mapper;

	@Override
	public PersonData getPersonById(Long personId) throws Exception{
		Optional<Person> person = personRepository.findById(personId);
		return person.isPresent() ? personMapper.toModel(person.get()) : null;
		
	}
	
	@Override
	public boolean isPersonQualificationAlreadyExist(Long personId, QualificationData qualification) throws Exception {
		return findByPersonAndQualification(personId, qualification) == null ? false : true;
	}

	@Override
	public PersonQualificationData updatePersonQualification(Long personId, QualificationData qualificationData) throws Exception{
		Person person = personRepository.findById(personId).get();
		Qualification qualification = qualificationRepository.findById(qualificationData.getId()).get();
		
		PersonQualification personQualification = new PersonQualification();
		personQualification.setPerson(person);
		personQualification.setQualification(qualification);
		personQualification.setCompleted(LocalDate.now());
		
		//TODO: Need to change the logic of setting Grade based on the requirement.
		personQualification.setGrade("PASS");
		personQualificationRepository.save(personQualification);
		
		//TODO: Need to change the logic of setting completed based on the requirement.
		person.setCompleted(true);
		personRepository.save(person);
		
		return mapper.toModel(personQualification);
		
	}
	
	private PersonQualification findByPersonAndQualification(Long personId, QualificationData qualificationData) throws Exception{
		Optional<Person> person = personRepository.findById(personId);
		if(person.isPresent()) {
			Optional<Qualification> qualification = qualificationRepository.findById(qualificationData.getId());
			if(qualification.isPresent()) {
				Optional<PersonQualification> personAndQualification = personQualificationRepository.findByPersonAndQualification(person.get(), qualification.get());
				if(personAndQualification.isPresent()) {
					return personAndQualification.get();
				}
			} else {
				throw new IllegalStateException("Qualification does not exist for qualification id : " + qualificationData.getId());
			}
		} else {
			throw new IllegalStateException("Person does not exist for person id : " + personId);
		}
		return null;
	}


	

}
