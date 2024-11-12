package com.example.authenticatingldap.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.authenticatingldap.entity.Person;
import com.example.authenticatingldap.model.PersonData;

@Component
public class PersonMapper {
	
	@Autowired
	private PersonQualificationMapper personQualificationMapper;
	
	public PersonData toModel(Person entity) {
		
		PersonData model = new PersonData();
		model.setId(entity.getId());
		model.setFirstName(entity.getFirstName());
		model.setSurName(entity.getSurName());
		model.setGender(entity.getGender());
		model.setCompleted(entity.isCompleted());
		model.setPersonQualifications(entity.getPersonQualifications().stream().map(pq -> personQualificationMapper.toModel(pq)).collect(Collectors.toSet()));
		return model;
		
	}
	
}
