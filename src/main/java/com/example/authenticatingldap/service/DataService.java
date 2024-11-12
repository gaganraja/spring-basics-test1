package com.example.authenticatingldap.service;

import com.example.authenticatingldap.model.PersonData;
import com.example.authenticatingldap.model.PersonQualificationData;
import com.example.authenticatingldap.model.QualificationData;

public interface DataService {
	
	PersonData getPersonById(Long personId) throws Exception;
	
	boolean isPersonQualificationAlreadyExist(Long personId, QualificationData qualification) throws Exception;

	PersonQualificationData createPersonQualification(Long valueOf, QualificationData qualification) throws Exception;

}
