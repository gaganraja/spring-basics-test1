package com.example.authenticatingldap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

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
	@GetMapping("/person")
	public Object getPersonData(String personId) {
		throw new UnsupportedOperationException("Complete the requirement as described in the comments above.");
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
	@PutMapping("/person-qualification")
	public void recordPersonQualification(String personId, Object qualification) {
		throw new UnsupportedOperationException("Complete the requirement as described in the comments above.");
	}


}
