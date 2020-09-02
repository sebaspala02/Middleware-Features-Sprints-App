package com.redhat.bluesmile.sprintsapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.redhat.bluesmile.sprintsapp.model.Features;

@EnableMongoRepositories
@Repository
public interface SprintsAppFeatureRepository extends MongoRepository<Features, Long> {
	
//	@Query("{ $or: [ { 'features.status': 0 }, { 'features.status': 1 }, { 'features.status': 2 } ] }")
//	List<BackLog> findByStatusFeatures();
	
//	@Query("{ 'features.status': { $lte: 2 } }, { _id:0, features:1 }")
//	List<BackLog> findbystatusfeatures();
	
//	@Query("{ 'features._id': featuresId },{_id:0, 'features.$':1}")
//	BackLog findByIdFeatures(@Param("featuresId")String featuresId);
	
//	@Query("{ 'features._id': ?0 },{_id:0, 'features.$':1}")
//	BackLog findByIdFeature(Long featuresId);
	
}