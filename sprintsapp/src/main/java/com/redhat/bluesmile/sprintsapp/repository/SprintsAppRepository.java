package com.redhat.bluesmile.sprintsapp.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.redhat.bluesmile.sprintsapp.model.BackLog;
import com.redhat.bluesmile.sprintsapp.model.Features;

@Repository
public interface SprintsAppRepository extends MongoRepository<BackLog, Long> {

	@Query("{ $or: [ { status: 0 }, { status: 1 }, { status: 2 } ] }")//, 'features.status': { '$lte': 2 } 
	List<BackLog> findByStatus();

//	@Query("{ $project: { features: { $filter: { input: \"$features\", as: \"features\", cond: { $lte: [ \"$$features.status\", 2 ] } } } } }")
//	List<BackLog> findByStatus();
	
	@Query("{ 'features.status': { '$lte': 2 } },{features:1,_id:0}")
	BackLog findByStatusFeatures();

	@Query("{ 'features.status': { '$lte': 2 } },{ _id:0, features:1 }")
	List<BackLog> findByStatusFeaturez();

	List<BackLog> findFeaturesByStatus();

//	@Query(value = "{ 'features._id': ?0 }", fields = "{_id:0,'features.$':1}")
//	BackLog findOne(Long featuresId);

//	@Query("{ _id: ?0 }, { $set: { ?1 } } ")
//	BackLog updateOne(String featuresId,String featuresDetails);	

	// @Param("featuresId")

}