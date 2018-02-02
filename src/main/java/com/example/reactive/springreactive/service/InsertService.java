package com.example.reactive.springreactive.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reactive.springreactive.model.Message;
import com.example.reactive.springreactive.model.Person;
import com.example.reactive.springreactive.repository.PersonRepository;

import reactor.core.publisher.Flux;

@Service
public class InsertService {
	
	@Autowired
	PersonRepository personRepository; 

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Flux<Person> perform(Object o, Message m) throws InterruptedException {
        logger.info("Perform Display: {}", o);
        Thread.sleep(100);
        
        // personRepository.find().subscribe(new PrintDocumentSubscriber());
        
        logger.info("Retrieved list of persons from MongoDB..");
        List<Person> pList = personRepository.findAll().collectList().block();
        pList.forEach(System.out::println);
        
        
        return personRepository.findAll();
    }
    
 
}
