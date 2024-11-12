package com.example.authenticatingldap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.authenticatingldap.entity.Qualification;

@Repository
public interface QualificationRepository extends CrudRepository<Qualification, Long>{

}
