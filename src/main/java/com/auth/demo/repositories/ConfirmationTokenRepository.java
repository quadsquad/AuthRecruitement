package com.auth.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.auth.demo.entities.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, String> {
   
	ConfirmationToken findByConfirmationToken(String confirmationToken);
	
	void deleteByConfirmationToken(String confirmationToken);
}
