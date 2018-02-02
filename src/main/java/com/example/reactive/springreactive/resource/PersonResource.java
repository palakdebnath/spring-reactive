package com.example.reactive.springreactive.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reactive.springreactive.model.Person;
import com.example.reactive.springreactive.repository.PersonRepository;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/persons")
public class PersonResource {

	private PersonRepository personRepository;
	
	public PersonResource(PersonRepository personRepository) {
		super();
		this.personRepository = personRepository;
	}

	@GetMapping("/all")
	public Flux<Person> getAll() {
		return personRepository.findAll();
	}
	
	
}
