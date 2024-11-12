package com.example.authenticatingldap.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.authenticatingldap.entity.Qualification;
import com.example.authenticatingldap.model.QualificationData;

@Component
public class QualificationMapper {
	
	@Autowired
	private PersonQualificationMapper personQualificationMapper;
	
	public QualificationData toModel(Qualification entity) {
		
		QualificationData model = new QualificationData();
		model.setId(entity.getId());
		model.setName(entity.getName());
		model.setDescription(entity.getDescription());
		model.setPersonQualifications(entity.getPersonQualifications().stream().map(pq -> personQualificationMapper.toModel(pq)).collect(Collectors.toSet()));
		return model;
		
	}

}
