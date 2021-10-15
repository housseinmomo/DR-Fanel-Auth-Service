package com.nethealth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nethealth.entities.Patient;

public interface PatientRepository extends MongoRepository<Patient, String>{
	
	Patient findByPersonEmail(String email);
	Patient findByPersonUsername(String username);

}
