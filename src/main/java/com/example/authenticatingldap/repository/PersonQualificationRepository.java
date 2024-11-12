package com.example.authenticatingldap.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.authenticatingldap.entity.Person;
import com.example.authenticatingldap.entity.PersonQualification;
import com.example.authenticatingldap.entity.Qualification;

@Repository
public interface PersonQualificationRepository extends CrudRepository<PersonQualification, Long>{
	
	Optional<PersonQualification> findByPersonAndQualification(Person person, Qualification qualification);

}
