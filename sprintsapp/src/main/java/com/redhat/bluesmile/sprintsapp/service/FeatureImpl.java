package com.redhat.bluesmile.sprintsapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.util.Assert;

import com.redhat.bluesmile.sprintsapp.model.BackLog;
import com.redhat.bluesmile.sprintsapp.model.Features;
import com.redhat.bluesmile.sprintsapp.repository.SprintsAppRepository;

public class FeatureImpl implements SprintsAppRepository {

	@Override
	public <S extends BackLog> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BackLog> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BackLog> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BackLog> S insert(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BackLog> List<S> insert(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BackLog> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BackLog> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<BackLog> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BackLog> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<BackLog> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<BackLog> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(BackLog entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends BackLog> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends BackLog> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BackLog> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BackLog> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends BackLog> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public List<BackLog> findByStatus(int status) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public BackLog findByStatusFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BackLog> findByStatusFeaturez() {
		// TODO Auto-generated method stub
		return null;
	}

//	private final MongoOperations mongoOperations;
//
//	@Autowired
//	public FeatureImpl(MongoOperations mongoOperations) {
//		Assert.notNull(mongoOperations);
//		this.mongoOperations = mongoOperations;
//	}

	private final MongoTemplate mongoTemplate;

	@Autowired
	public FeatureImpl(MongoTemplate mongoTemplate) {
//		Assert.notNull(mongoTemplate);
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<BackLog> findFeaturesByStatus() {
		// TODO Auto-generated method stub 

//		Query query = TextQuery.queryText((new TextCriteria().matching("BackLog").notMatchingAny("id,title,description")));
		BasicQuery query = new BasicQuery("{ 'features.status': { '$lte': 2 } },{ _id:0, features:1 }");
//		List<BackLog> result = (List<BackLog>) this.mongoOperations.find(query, BackLog.class);
		List<BackLog> result = mongoTemplate.find(query, BackLog.class);
		return result;
	}

	@Override
	public List<BackLog> findByStatus() {
		// TODO Auto-generated method stub
		return null;
	}

}
