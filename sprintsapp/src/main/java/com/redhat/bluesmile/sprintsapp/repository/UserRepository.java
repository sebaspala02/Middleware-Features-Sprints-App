package com.redhat.bluesmile.sprintsapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.redhat.bluesmile.sprintsapp.model.UserModel;

public interface UserRepository extends MongoRepository<UserModel, Long>{

	UserModel findByEmail(String email);
	
}
