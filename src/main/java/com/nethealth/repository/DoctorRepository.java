package com.nethealth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nethealth.entities.Doctor;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
	Doctor findByPersonEmail(String email);
	Doctor findByPersonUsername(String username);


}
 