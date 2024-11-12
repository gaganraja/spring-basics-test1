package com.example.authenticatingldap.mapper;

import org.springframework.stereotype.Component;

import com.example.authenticatingldap.entity.PersonQualification;
import com.example.authenticatingldap.model.PersonQualificationData;

@Component
public class PersonQualificationMapper {
	
	public PersonQualificationData toModel(PersonQualification entity) {
		
		PersonQualificationData model = new PersonQualificationData();
		model.setId(entity.getId());
		model.setPersonId(entity.getPerson().getId());
		model.setQualificationId(entity.getQualification().getId());
		model.setCompleted(entity.getCompleted());
		model.setGrade(entity.getGrade());
		
		return model;
	}

}
