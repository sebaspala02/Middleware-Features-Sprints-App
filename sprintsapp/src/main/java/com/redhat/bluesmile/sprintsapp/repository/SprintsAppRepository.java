package com.redhat.bluesmile.sprintsapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.redhat.bluesmile.sprintsapp.model.BackLog;
import com.redhat.bluesmile.sprintsapp.model.UserModel;

@Repository
public interface SprintsAppRepository extends MongoRepository<BackLog, Long> {}