package com.auth.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.auth.demo.entities.UserModel;

@Repository
public interface UserModelRepository extends MongoRepository<UserModel, String> {

}
