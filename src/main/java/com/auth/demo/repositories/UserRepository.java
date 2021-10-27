package com.auth.demo.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.auth.demo.entities.UserModel;

public interface UserRepository extends MongoRepository<UserModel,String> {

	UserModel findByUsername(String username);
	Optional<UserModel> findById(String id);
	
}
