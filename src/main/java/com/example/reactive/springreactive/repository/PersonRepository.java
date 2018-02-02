package com.example.reactive.springreactive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.reactive.springreactive.model.Person;

public interface PersonRepository extends ReactiveMongoRepository<Person, String> {

}
