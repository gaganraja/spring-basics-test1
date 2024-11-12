package com.example.authenticatingldap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.authenticatingldap.exception.DataAccessException;
import com.example.authenticatingldap.model.PersonData;
import com.example.authenticatingldap.model.PersonQualificationData;
import com.example.authenticatingldap.model.QualificationData;
import com.example.authenticatingldap.service.DataService;
import com.example.authenticatingldap.service.impl.DataServiceImpl;

@RestController
public class DataController {

	/**
	 * Modify the getPersonData method to:
	 * 
	 * Retrieve all the details for the given personId
	 * Unit test the getPersonData
	 * Assume the H2 database will be replaced in production
	 * log the request
	 * 
	 * ** 
	 * ** The method signatures can be modified to reflect more appropriate classes
	 * ** 
	 * @param personId
	 * @return
	 */
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);
	
	@Autowired
	private DataService dataService;
	
	@GetMapping("/person/{id}")
	@PreAuthorize("hasAnyRole('ROLE_MANAGERS', 'ROLE_DEVELOPERS')")
	public ResponseEntity<PersonData> getPersonData(@PathVariable(name = "id", required=true) String personId) {
		LOGGER.info("Request received to retrieve Person data for person id : " + personId);
		Long id = null;
		try {
			id = Long.valueOf(personId);
		} catch (NumberFormatException ex) {
			LOGGER.error("Person Id : " + personId + " is not a number");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		PersonData person = null;
		try {
			person = dataService.getPersonById(id);
		} catch (Exception ex) {
			LOGGER.error("Error fetching Person details with Person Id : "  + personId, ex );
			throw new DataAccessException("Error fetching Person details with Person Id : "  + personId );
		}
		return new ResponseEntity<>(person, HttpStatus.OK);
		
	}



	/**
	 * Modify the recordPersonQualification method to
	 * 
	 * Prohibit any user not part of the "manager" roles [user "ben" is a manager, user "bob" is not a manager]
	 * Update the person to record a new qualification completed against the person
	 * Demonstrate validation	 
	 * Unit test the setPersonData
	 * Assume the H2 database will be replaced in production
	 * log the request 
	 * ** 
	 * ** The method signatures can be modified to reflect more appropriate classes
	 * **  
	 * @param personData - 
	 */
	
	//TODO: Assumptions : A)Qualification passed in Request Body already exists in DB, if not, do we need to create it first? B)Associating a qualification to person, so grade
	//TODO: will always be 'PASS', if not, where will we get grade from? C)Same person won't have same qualification multiple times, so a validation is done to check if personqualification
	//TODO: pair already exists.D)Whats the criteria for setting person.complete=true? for now i'm setting it to true even if one qualification is attached to the person..
	
	@PutMapping("/person-qualification/{id}")
	@PreAuthorize("hasRole('ROLE_MANAGERS')")
	public ResponseEntity<PersonQualificationData> recordPersonQualification(@PathVariable(name = "id", required=true) String personId, @RequestBody QualificationData qualification) {
		LOGGER.info("PUT request to associate Qualification with Id : " + qualification.getId() + " to Person with Id : " + personId);
		//check if person exists..
		if(getPersonData(personId).getBody() == null) {
			throw new IllegalStateException("person not found for id : " + personId);
		}
		//check if personQualification already exist
		boolean personQualificationAlreadyExist = false;
		try {
			personQualificationAlreadyExist = dataService.isPersonQualificationAlreadyExist(Long.valueOf(personId), qualification);
		} catch (Exception ex) {
			LOGGER.error("Error checking PersonQualification details for Person Id : "  + personId + " and qualification id : " + qualification.getId(), ex );
			throw new DataAccessException("Error checking PersonQualification details for Person Id : "  + personId + " and qualification id : " + qualification.getId());
		}
		if(personQualificationAlreadyExist) {
			throw new IllegalStateException("PersonQualification already exist");
		}
		PersonQualificationData personQualificationData = null;
		try {
			personQualificationData = dataService.updatePersonQualification(Long.valueOf(personId), qualification);
		} catch (Exception ex) {
			LOGGER.error("Error creating PersonQualification and associating with person with Id : "  + personId, ex );
			throw new DataAccessException("Error creating PersonQualification and associating with person with Id : "  + personId + qualification.getId());
		}
		
		return new ResponseEntity<>(personQualificationData, HttpStatus.OK);
		
	}


}
