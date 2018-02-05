package com.example.reactive.springreactive.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import com.example.reactive.springreactive.model.Pubsub;

import reactor.core.publisher.Flux;

public interface PubsubRepository extends ReactiveMongoRepository<Pubsub, String> {

	@Tailable 
	//@Query("{'type': ?0 }")
	@Query("{ 'type': { $in: ?0 } } ")
	Flux<Pubsub> findWithTailableCursorBy(List<String> type);
}
