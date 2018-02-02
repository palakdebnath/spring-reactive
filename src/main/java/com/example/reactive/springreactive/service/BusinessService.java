package com.example.reactive.springreactive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void perform(Object o) {
        logger.info("Perform: {}", o);
        logger.info("H E L L O  from G R E E T I N G  Service :-)");
    }
}
