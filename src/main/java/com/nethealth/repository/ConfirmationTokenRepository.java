package com.nethealth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nethealth.entities.ConfirmationToken;

public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, String> {
	
	ConfirmationToken findByConfirmationToken(String confirmationToken);

}
